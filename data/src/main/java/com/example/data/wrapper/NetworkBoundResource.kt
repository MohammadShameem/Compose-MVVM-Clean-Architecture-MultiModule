package com.example.data.wrapper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.JsonParser
import com.example.entity.res.ApiResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

abstract class NetworkBoundResource<ResultType>{

    private val result = MediatorLiveData<ApiResponse<ResultType>>()
    private val disposable = CompositeDisposable()
    init {
        downloadNetworkData()
    }

    private fun setValue(newValue: ApiResponse<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun downloadNetworkData(){
        disposable.add(
            createCall()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { setValue(ApiResponse.loading(true)) }
                .doOnEvent { _, _ ->
                    run {
                        disposable.clear()
                        setValue(ApiResponse.loading(false))
                    }
                }
                .subscribeWith(object : DisposableSingleObserver<ResultType>(){
                    override fun onSuccess(data: ResultType) {
                        result.value = ApiResponse.success(data)
                    }

                    override fun onError(e: Throwable?) {
                        Timber.e(e?.message.toString())
                        result.value = ApiResponse.failure(
                            message = message(e),
                            code = code(e)
                        )
                    }

                })
        )
    }
    private fun message(throwable: Throwable?):String{
        when (throwable) {
            is SocketTimeoutException -> return "Whoops! connection time out, try again!"
            is IOException -> return "No internet connection, try again!"
            is HttpException -> return try {
                val errorJsonString = throwable.response()?.errorBody()?.string()
                val errorMessage = JsonParser.parseString(errorJsonString).asJsonObject["message"].asString
                if (errorMessage.isEmpty())"Whoops! Something went wrong"
                else errorMessage
            }catch (e:Exception){
                "Unknown error occur, please try again!"
            }
        }
        return "Unknown error occur, please try again!"
    }

    private fun code(throwable: Throwable?):Int{
        return if (throwable is HttpException) (throwable).code()
        else  0
    }

    protected abstract fun createCall(): Single<ResultType>
    fun asLiveData() = result as LiveData<ApiResponse<ResultType>>


}