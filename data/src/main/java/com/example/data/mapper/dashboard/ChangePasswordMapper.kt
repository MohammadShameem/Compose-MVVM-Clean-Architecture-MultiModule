package com.example.data.mapper.dashboard

import com.example.apiresponse.dashboard.ChangePasswordApiResponse
import com.example.data.mapper.Mapper
import com.example.entity.dashboard.ChangePasswordApiEntity
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