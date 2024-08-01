package com.module.persistence.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseConfig {

    @Value("\${spring.datasource.url}")
    private lateinit var dataSourceUrl: String

    @Value("\${spring.datasource.username}")
    private lateinit var dataSourceUsername: String

    @Value("\${spring.datasource.password}")
    private lateinit var dataSourcePassword: String

    @Bean
    fun databaseInitializer(dataSource: DataSource): CommandLineRunner {
        return CommandLineRunner {
            dataSource.connection.use { connection ->
                val statement = connection.createStatement()

                // Create members table
                statement.execute(
                    """
                    CREATE TABLE IF NOT EXISTS members (
                        created_at         datetime(6)  not null,
                        id                 bigint auto_increment primary key,
                        last_modified_at   datetime(6)  not null,
                        phone_number       varchar(30)  not null,
                        user_id            varchar(255) not null,
                        soft_delete_status varchar(16)  not null,
                        INDEX idx_members__phone_number (phone_number),
                        INDEX idx_members__user_id (user_id)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
                """.trimIndent()
                )

                // Create member_credentials table
                statement.execute(
                    """
                    CREATE TABLE IF NOT EXISTS member_credentials (
                        created_at       datetime(6) not null,
                        id               bigint auto_increment primary key,
                        last_modified_at datetime(6) not null,
                        member_id        bigint      not null,
                        password         text        not null,
                        salt             text        not null,
                        INDEX idx_member_credentials__member_id (member_id)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
                """.trimIndent()
                )

                // Create products table
                statement.execute(
                    """
                    CREATE TABLE IF NOT EXISTS products (
                        cost               double       not null,
                        size               varchar(32)  not null,
                        created_at         datetime(6)  not null,
                        expiry_date_at     datetime(6)  not null,
                        file_id            bigint       null,
                        id                 bigint auto_increment primary key,
                        last_modified_at   datetime(6)  not null,
                        member_id          bigint       not null,
                        price              bigint       not null,
                        bar_code           varchar(255) not null,
                        description        text         not null,
                        initial_consonant  varchar(124) not null,
                        name               varchar(255) not null,
                        category           varchar(64)  not null,
                        soft_delete_status varchar(16)  not null,
                        INDEX idx_products__created_at (created_at),
                        INDEX idx_products__member_id (member_id)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
                """.trimIndent()
                )

                // Create file_metadatas table
                statement.execute(
                    """
                    CREATE TABLE IF NOT EXISTS file_metadatas (
                        created_at         datetime(6)  not null,
                        id                 bigint auto_increment primary key,
                        last_modified_at   datetime(6)  not null,
                        member_id          bigint       not null,
                        actual_url         text         null,
                        file_key           varchar(255) not null,
                        original_file_name varchar(255) not null,
                        soft_delete_status varchar(16)  not null,
                        vendor             varchar(32)  null,
                        INDEX idx_file_metadatas__member_id (member_id)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
                """.trimIndent()
                )
            }
        }
    }
}