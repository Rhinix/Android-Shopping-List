package be.amellaa.shoppinglist.dto

/**
 *  Interface for communicating data from the WebApi
 */
interface ICommunicateData<T> : ICommunicateCode {
    fun communicateData(data: T)
}