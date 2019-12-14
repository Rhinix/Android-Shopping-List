package be.amellaa.shoppinglist.dto

interface ICommunicateData<T> : ICommunicateCode {
    fun communicateData(data: T)
}