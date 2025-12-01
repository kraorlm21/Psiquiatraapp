package com.example.psique_simple.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\r8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0011"}, d2 = {"Lcom/example/psique_simple/api/RetrofitClient;", "", "()V", "URL_BACKEND", "", "URL_CLIMA", "climaApi", "Lcom/example/psique_simple/api/ClimaApi;", "getClimaApi", "()Lcom/example/psique_simple/api/ClimaApi;", "climaApi$delegate", "Lkotlin/Lazy;", "psiquiatriaApi", "Lcom/example/psique_simple/api/PsiquiatriaApiService;", "getPsiquiatriaApi", "()Lcom/example/psique_simple/api/PsiquiatriaApiService;", "psiquiatriaApi$delegate", "app_release"})
public final class RetrofitClient {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String URL_BACKEND = "http://10.0.0.2:8080/";
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy psiquiatriaApi$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String URL_CLIMA = "https://api.open-meteo.com/";
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy climaApi$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.psique_simple.api.RetrofitClient INSTANCE = null;
    
    private RetrofitClient() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.psique_simple.api.PsiquiatriaApiService getPsiquiatriaApi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.psique_simple.api.ClimaApi getClimaApi() {
        return null;
    }
}