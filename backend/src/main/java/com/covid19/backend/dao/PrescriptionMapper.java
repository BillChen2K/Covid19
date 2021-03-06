package com.covid19.backend.dao;

import com.covid19.backend.model.Prescription;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;

// MyBatis
@Mapper
@Component
public interface PrescriptionMapper {
    @Select("SELECT * from prescription where prescription_id=#{prescription_id}")
    public Prescription selectPrescriptionByID(@Param("prescription_id") long prescription_id);

    @Select("SELECT count(*) " +
            "from prescription")
    public Integer getNumber();

    @Select("SELECT * " +
            "from prescription natural join doctor natural join medicine " +
            "where patient_id=#{patient_id}")
    public ArrayList<HashMap<String,String>> selectPrescriptionByPatientID(@Param("patient_id") String patient_id);

    @Select("SELECT * from prescription where " +
            "patient_id like concat('%',#{patient_id},'%')" +
            "and medicine_id like concat('%',#{medicine_id},'%')" +
            "and dosage like concat('%',#{dosage},'%') " +
            "and `usage` like concat('%',#{usage},'%')" +
            "and doctor_id like concat('%',#{doctor_id},'%')")
    public ArrayList<Prescription> selectPrescription(
            @Param("patient_id") String patient_id,
            @Param("medicine_id") String medicine_id,
            @Param("dosage") String dosage,
            @Param("usage") String usage,
            @Param("doctor_id") String doctor_id
    );

    @Select("SELECT * FROM prescription WHERE patient_id = #{patient_id}")
    ArrayList<Prescription> selectPrescriptionByPatientId(@Param("patient_id") long patient_id);

    @Insert("insert into prescription(" +
            "patient_id, " +
            "medicine_id, " +
            "doctor_id," +
            "dosage, " +
            "`usage`) " +
            "values (" +
            "#{patient_id}, " +
            "#{medicine_id}, " +
            "#{doctor_id}, " +
            "#{dosage}, " +
            "#{usage})"
    )
    @SelectKey(statement = "SELECT LAST_INSERT_ID() as prescription_id", keyProperty = "prescription_id", before = false, resultType = long.class)
    long insertPrescription(Prescription prescription);

    @Update("Update prescription set " +
            "patient_id=#{patient_id}, " +
            "medicine_id=#{medicine_id}, " +
            "doctor_id=#{doctor_id}" +
            "dosage=#{dosage}, " +
            "`usage`=#{usage}, " +
            "where prescription_id=#{prescription_id}")
    void updatePrescription(Prescription prescription);

    @Delete("delete from prescription where prescription_id = #{prescription_id}")
    void deletePrescriptionByID(@Param("prescription_id") long prescription_id);
}
