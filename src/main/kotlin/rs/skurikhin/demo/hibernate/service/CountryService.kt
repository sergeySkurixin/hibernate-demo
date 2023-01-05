package rs.skurikhin.demo.hibernate.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.skurikhin.demo.hibernate.bean.Country
import rs.skurikhin.demo.hibernate.repository.JpaCountryRepository

@Service
class CountryService(
    @Autowired private var countryRepository: JpaCountryRepository,
) {

    fun findById(cnrId: Int?): Country {
        if (cnrId == null) {
            throw nullValueException("cnrId")
        }
        return countryRepository.findById(cnrId).orElseThrow { countryNotExists(cnrId) }!!
    }

    fun findCountry(countryName: String?): Country {
        if (countryName == null) {
            throw nullValueException("countryName")
        }
        return countryRepository.findByCountryName(countryName) ?: throw countryNotExists(countryName)
    }

    private fun nullValueException(propName: String?) = RuntimeException("Expected nonNull value, propName=$propName")
    private fun countryNotExists(cnrId: Int?) = RuntimeException("Country not exists, cnrId=$cnrId")
    private fun countryNotExists(cnrName: String?) = RuntimeException("Country not exists, countryName=$cnrName")

    companion object {
        private val log = LoggerFactory.getLogger(CountryService::class.java)
    }
}