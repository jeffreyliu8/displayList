package com.example.displaylist

import com.example.displaylist.model.Country
import com.example.displaylist.model.Currency
import com.example.displaylist.model.Language
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `test Retrofit Instance and endpoint call`() = runBlocking {

        val instance = com.example.displaylist.endpoint.WebEndpointObj.getInstance()
        val counties = instance.getCountries()
        assert(instance.getCountries().size == 249)
        assert(
            counties.contains(
                Country(
                    capital = "Washington, D.C.",
                    name = "United States of America",
                    code = "US",
                    region = "NA",
                    flag = "https://restcountries.eu/data/usa.svg",
                    language = Language(
                        code = "en",
                        name = "English"
                    ),
                    currency = Currency(
                        code = "USD",
                        name = "United States dollar",
                        symbol = "$"
                    ),
                )
            )
        )
    }
}