package com.jatri.data.mapper.dashboard

import com.jatri.apiresponse.dashboard.ChangePasswordApiResponse
import com.jatri.data.mapper.Mapper
import com.jatri.entity.dashboard.ChangePasswordApiEntity
import javax.inject.Inject

class ChangePasswordMapper @Inject constructor() :
Mapper<ChangePasswordApiResponse, ChangePasswordApiEntity>{
    override fun mapFromApiResponse(type: ChangePasswordApiResponse): ChangePasswordApiEntity {
        return ChangePasswordApiEntity(
            message = type.message,
            code = type.code
        )
    }

}