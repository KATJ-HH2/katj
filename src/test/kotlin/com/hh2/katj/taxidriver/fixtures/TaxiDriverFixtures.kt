package com.hh2.katj.taxidriver.fixtures

import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxidriver.model.entity.TaxiDriverStatus
import com.hh2.katj.util.model.Gender
import com.hh2.katj.util.model.RoadAddress
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import java.time.LocalDate

class TaxiDriverFixtures {
    companion object {
        fun 드라이버_생성(taxi: Taxi,
                     driverLicenseId: String,
                     issueDate: LocalDate,
                     securityId: String,
                     name: String,
                     status: TaxiDriverStatus,
                     gender: Gender,
                     address: RoadAddress,
                     img: String): String {
            val params: MutableMap<String, Any> = mutableMapOf()

            params.put("taxi", taxi.toString())
            params.put("driverLicenseId", driverLicenseId)
            params.put("issueDate", issueDate.toString())
            params.put("securityId", securityId)
            params.put("name", name)
            params.put("status", status.toString())
            params.put("gender", gender.toString())
            params.put("address", address.toString())
            params.put("img", img)

            return given().log().all()
                    .body(params).contentType(ContentType.JSON)
                        .`when`()
                    .post("/taxidriver/enroll")
                        .then().log().all()
                        .extract().jsonPath().getString("securityId")
        }
    }
}