package com.jatri.data.mapper.credential


import com.jatri.apiresponse.offlinecounterticketing.UserProfileApiResponse
import com.jatri.data.mapper.Mapper
import com.jatri.di.qualifier.AppImageBaseUrl
import com.jatri.entity.credential.UserProfileApiEntity
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import javax.inject.Inject

class UserProfileApiMapper @Inject constructor() :
    Mapper<UserProfileApiResponse, UserProfileApiEntity> {

    override fun mapFromApiResponse(type: UserProfileApiResponse): UserProfileApiEntity {
        return UserProfileApiEntity(
            address = type.user.address?: "",
            balance = type.user.balance?: "",
            city = type.user.city?: "",
            created_at = type.user.created_at?: "",
            date_of_birth = type.user.date_of_birth?: "",
            device_id = type.user.device_id?: "",
            device_token = type.user.device_token?: "",
            device_type = type.user.device_type?: "",
            email = type.user.email?: "",
            first_name = type.user.first_name?: "",
            gender = type.user.gender?: "",
            id = type.user.id?: -1,
            image = type.user.image?: "",
            last_name = type.user.last_name?: "",
            my_ref_code = type.user.my_ref_code?: "",
            phone = type.user.phone?: "",
            refer_by = type.user.refer_by?: "",
            role_id = type.user.role_id?: -1,
            tracking_active_to_date = type.user.tracking_active_to_date?: "",
            tracking_status = type.user.tracking_status?: -1,
            updated_at = type.user.updated_at?: "",
            point = type.user.point?:0.0,
            google_cloud_api_key = type.user.g_m_a?.android_user?:""
        )
    }


    class CacheProfile @Inject constructor(var helper: SharedPrefHelper, @AppImageBaseUrl var url:String){
        fun cacheProfileData(userProfileApiEntity: UserProfileApiEntity){
            helper.putString(SpKey.googleCloudApiKey, userProfileApiEntity.google_cloud_api_key)
            helper.putString(SpKey.phoneNumber, userProfileApiEntity.phone)
            helper.putString(SpKey.firstName, userProfileApiEntity.first_name)
            helper.putString(SpKey.lastName, userProfileApiEntity.last_name)
            helper.putString(SpKey.fullName,"${userProfileApiEntity.first_name} ${userProfileApiEntity.last_name}")
            helper.putString(SpKey.address, userProfileApiEntity.address)
            helper.putString(SpKey.gender, userProfileApiEntity.gender)
            helper.putString(SpKey.email, userProfileApiEntity.email)
            helper.putString(SpKey.dob, userProfileApiEntity.date_of_birth)
            helper.putInt(SpKey.userId, userProfileApiEntity.id)
            helper.putString(SpKey.city, userProfileApiEntity.city)
            helper.putString(SpKey.profileImageUrl,"$url${userProfileApiEntity.image}")
            helper.putString(SpKey.myRefCode, userProfileApiEntity.my_ref_code)
            helper.putBool(SpKey.loginStatus,true)
        }
    }

}