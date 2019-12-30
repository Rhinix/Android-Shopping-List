package be.amellaa.shoppinglist.dto

/**
 *  Interface for communicating data
 */
interface ICommunicateData<T> : ICommunicateCode {
    fun communicateData(data: T)
}