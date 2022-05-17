package com.jatri.data.mapper.dashboard

import com.jatri.apiresponse.dashboard.ChangePasswordProfileInfoApiResponse
import com.jatri.apiresponse.login.LoginApiResponse
import com.jatri.data.mapper.Mapper
import com.jatri.entity.dashboard.ChangePasswordProfileInfoApiEntity
import com.jatri.entity.login.LoginEntity
import javax.inject.Inject

class ChangePasswordProfileInfoMapper @Inject constructor() :
Mapper<ChangePasswordProfileInfoApiResponse, ChangePasswordProfileInfoApiEntity>{
    override fun mapFromApiResponse(type: ChangePasswordProfileInfoApiResponse): ChangePasswordProfileInfoApiEntity {
        return ChangePasswordProfileInfoApiEntity(
            userName = type.profile.name,
            phoneNumber = type.profile.mobile
        )
    }

}