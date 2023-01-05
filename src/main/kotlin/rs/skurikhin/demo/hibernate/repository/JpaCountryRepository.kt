package rs.skurikhin.demo.hibernate.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rs.skurikhin.demo.hibernate.bean.Country

@Repository
interface JpaCountryRepository : JpaRepository<Country?, Int?> {
    fun findByCountryName(countryName: String): Country?
}

