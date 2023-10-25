package com.hh2.katj.taxi.fixtures

import com.hh2.katj.taxi.model.ChargeType
import com.hh2.katj.taxi.model.FuelType
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import java.time.LocalDate

class TaxiTestFixtures {
    companion object {
        fun 택시_생성(carNo: String, vin: String, kind: ChargeType, manufactureDate: LocalDate, fuel: FuelType, color: String, insureYn: Boolean, accidentYn: Boolean): ExtractableResponse<Response> {
            val params: MutableMap<String, String> = mutableMapOf()

            params.put("carNo", carNo)
            params.put("vin", vin)
            params.put("kind", kind.toString())
            params.put("manufactureDate", manufactureDate.toString())
            params.put("fuel", fuel.toString())
            params.put("color", color)
            params.put("insureYn", insureYn.toString())
            params.put("accidentYn", accidentYn.toString())

            return given().log().all()
                    .body(params).contentType(ContentType.JSON)
                        .`when`()
                    .post("/taxi")
                        .then().log().all()
                        .extract()
        }
    }
}