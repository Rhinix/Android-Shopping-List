package be.amellaa.shoppinglist.dao

interface ICommunicateData<T> : ICommunicateCode {
    fun communicateData(data: T)
}