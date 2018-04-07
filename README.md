# jwt

A rajouter dans la classe WebSecurityConfig pour ne pas être bloqué par les requetes "OPTION" qui sont envoyées avant le "POST"
```JAVA
 @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
```
