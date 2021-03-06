package com.covid19.backend.service.hospital;

import com.covid19.backend.controller.BaseController;
import com.covid19.backend.model.Result;
import com.covid19.backend.model.Hospital;
import com.covid19.backend.service.BaseService;
import com.covid19.backend.service.hospital.GetHospitalInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.covid19.backend.utils.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
public class UpdateHospitalService extends BaseService{
    @Autowired
    public GetHospitalInfoService getHospitalInfoService;

    public int checkCurrentUserInfo(HttpServletRequest request)
    {
        Long uid = Authentication.getCurrentUid(request);
        if (uid == null) return -1;
        return 0;
    }

    public Hospital updateHospital(Hospital new_hospital)
    {
        Hospital hospital = getHospitalInfoService.geHospitalInfoByID(new_hospital.getHospital_id());
        hospital.setHospital_name(new_hospital.getHospital_name()).setAddress(new_hospital.getAddress());
        hospitalMapper.updateHospital(hospital);
        return hospital;
    }
}
