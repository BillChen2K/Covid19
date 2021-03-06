package com.covid19.backend.controller.prescription;

import com.covid19.backend.controller.BaseController;
import com.covid19.backend.model.Result;
import com.covid19.backend.model.Prescription;
import com.covid19.backend.service.prescription.GetPrescriptionInfoService;
import com.github.pagehelper.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 获取处方信息的控制器
 **/
@RestController
@Api(tags = "处方控制器", value = "和处方有关的控制器")
public class GetPrescriptionInfo {
    @Autowired
    public GetPrescriptionInfoService getPrescriptionInfoService;

    @PostMapping("/prescription/getPrescriptionInfoByID")
    @ApiOperation(value = "根据处方ID获取处方信息", notes = "可以根据处方ID获取处方信息")
    @ApiImplicitParam(name = "prescription_id",value = "处方ID")
    public Result<Prescription> getPrescriptionInfoByID(
            @RequestParam(value = "prescription_id") long prescription_id
    )
    {
        Prescription prescription = getPrescriptionInfoService.getPrescriptionInfoByID(prescription_id);
        if(prescription == null) return Result.error(2012,"不存在该处方记录");
        return Result.ok(prescription);
    }

    @PostMapping("/prescription/getPrescriptionNumber")
    @ApiOperation(value = "获取当前数据库处方数量", notes = "可以获取当前数据库处方数量")
    public Result<Prescription> getPrescriptionNumber()
    {
        return Result.ok(getPrescriptionInfoService.getPrescriptionNumber());
    }

    @PostMapping("/prescription/getPrescriptionInfoByPatientID")
    @ApiOperation(value = "根据病人ID获取用药信息", notes = "可以根据病人ID获取用药信息")
    @ApiImplicitParam(name = "patient_id",value = "病人ID")
    public Result<Prescription> getPrescriptionInfoByPatientID(
            @RequestParam(value = "patient_id") String patient_id
    )
    {
        ArrayList<HashMap<String,String>> list = getPrescriptionInfoService.gePrescriptionInfoByPatientID(patient_id);
        if(list == null) return Result.error(2012,"不存在这样的用药记录");
        return Result.ok(list);
    }

    @PostMapping("/prescription/getPrescriptionInfo")
    @ApiOperation(value = "根据处方除ID外属性获取处方信息", notes = "可以根据处方除ID外属性获取处方信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patient_id",value = "病人ID"),
            @ApiImplicitParam(name = "medicine_id",value = "药品ID"),
            @ApiImplicitParam(name = "dosage",value = "剂量"),
            @ApiImplicitParam(name = "usage",value = "用法"),
            @ApiImplicitParam(name = "doctor_id",value = "医生ID")
    })
    public Result<ArrayList<Prescription>> getPrescriptionInfo(
            @RequestParam(value = "patient_id") String patient_id,
            @RequestParam(value = "medicine_id")String medicine_id,
            @RequestParam(value = "dosage",required = false)String dosage,
            @RequestParam(value = "usage",required = false)String usage,
            @RequestParam(value = "doctor_id",required = false)String doctor_id,
            @RequestParam Integer page, // 分页
            @RequestParam Integer size
    )
    {
        Page<HashMap<String, String>> pageInfo = PageHelper.startPage(page, size);
        ArrayList<Prescription> list = getPrescriptionInfoService.getPrescriptionInfo(
                patient_id,
                medicine_id,
                dosage,
                usage,
                doctor_id);
        if (list == null) return null;
        return Result.pagedOk(pageInfo);
    }

    @PostMapping("/prescription/getPrescriptionInfoByPatientId")
    @ApiOperation(value = "根据病人 ID 获取处方信息", notes = "可以根据处方除ID外属性获取处方信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patient_id",value = "病人ID"),
    })
    public Result<ArrayList<Prescription>> getPrescriptionInfoByPatientId(
            @RequestParam(value = "patient_id") long patient_id,
            @RequestParam Integer page, // 分页
            @RequestParam Integer size
    )
    {
        Page<HashMap<String, String>> pageInfo = PageHelper.startPage(page, size);
        ArrayList<Prescription> list = getPrescriptionInfoService.getPrescriptionByPatientId(patient_id);
        if (list == null) return null;
        return Result.pagedOk(pageInfo);
    }
}
