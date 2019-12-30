package be.amellaa.shoppinglist.dto

/**
 *  Interface to communicate a HTTP code from the WebApi
 */
interface ICommunicateCode {
    fun communicateCode(code: Int)
}