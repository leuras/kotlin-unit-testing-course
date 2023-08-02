package br.com.leuras.app.infrastructure.legacy

import org.springframework.jdbc.datasource.DriverManagerDataSource

object DataSourceFactory {

    private val instance = DriverManagerDataSource()

    init {
        instance.setDriverClassName("org.postgresql.Driver")
        instance.url = "jdbc:postgresql://localhost:5432/postgres"
        instance.username = System.getenv("DB_USERNAME")
        instance.password = System.getenv("DB_PASSWORD")
    }

    fun getInstance() = this.instance
}