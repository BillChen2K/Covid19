package com.covid19.backend.service.prescription;

import com.covid19.backend.dao.PrescriptionMapper;
import com.covid19.backend.model.Prescription;
import com.covid19.backend.service.BaseService;
import com.covid19.backend.utils.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class GetPrescriptionInfoService extends BaseService{
    /**
     * 根据用药ID获取用药信息
     */
    public Prescription gePrescriptionInfoByID(long prescription_id){
        return prescriptionMapper.selectPrescriptionByID(prescription_id);
    }

    /**
     * 根据用药其他属性获取用药信息
     */
    public ArrayList<Prescription> getPrescriptionInfo(
            String patient_id,
            String medicine_id,
            String dosage,
            String usage
    )
    {
        if(patient_id == null) patient_id = "%";
        if(medicine_id == null) medicine_id = "%";
        if(dosage == null) dosage = "%";
        if(usage == null) usage = "%";

        return prescriptionMapper.selectPrescription(
                patient_id,
                medicine_id,
                dosage,
                usage);
    }

    public ArrayList<Prescription> getPrescriptionByPatientId(long patientId){
        return prescriptionMapper.selectPrescriptionByPatientId(patientId);
    }
}
