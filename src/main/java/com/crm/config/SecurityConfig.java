    package com.crm.config;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

    import java.util.List;

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    public class SecurityConfig{
    //    @Bean
    //    public UserDetailsService userDetailsService() {
    //        UserDetails user = User.builder().username("admin").
    //                password("$2a$12$ZRO033PEfdBBG85XbG0Ft.3cxWn0t5Ca7fMgEAKXTH3O0jiG3qz4a")
    //                .build();
    //        return new InMemoryUserDetailsManager(user);
    //    }

        @Bean
        public CorsConfigurationSource coreConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:5173"));
            configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
            configuration.setAllowedHeaders(List.of("*"));
            configuration.setAllowCredentials(true);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**",configuration);
            return source;
        }
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) {
            http.
                    cors(cors -> cors.configurationSource(coreConfigurationSource()))

                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth.requestMatchers("/public/**", "/", "/public")
                            .permitAll().requestMatchers("/admin/**")
                            .authenticated().anyRequest().authenticated())
                    .formLogin(Customizer.withDefaults())
                    .logout(Customizer.withDefaults())
                    .httpBasic(Customizer.withDefaults());
            return http.build();
        }
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

    }
