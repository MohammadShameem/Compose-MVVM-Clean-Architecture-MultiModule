package com.jatri.data.mapper.login

import com.jatri.apiresponse.login.LoginApiResponse
import com.jatri.data.mapper.Mapper
import com.jatri.entity.login.LoginEntity
import javax.inject.Inject

class LoginMapper @Inject constructor() :
Mapper<LoginApiResponse, LoginEntity>{
    override fun mapFromApiResponse(type: LoginApiResponse): LoginEntity {
        return LoginEntity(
            message = type.message?: "",
            status = type.status?: "",
            token = type.token?: "",
            address = type.offline_counterman.address?: "",
            company_id = (type.offline_counterman.company_id?:"") as Int,
            created_at = type.offline_counterman.created_at?: "",
            id = (type.offline_counterman.id?:"") as Int,
            mobile = type.offline_counterman.mobile?: "",
            name = type.offline_counterman.name?: "",
            updated_at = type.offline_counterman.updated_at?: ""
        )
    }

}