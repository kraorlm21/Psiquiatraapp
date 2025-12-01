package com.example.psique_simple.vistas;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a>\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a<\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000e2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u0007\u001a\u001e\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u00a8\u0006\u0013"}, d2 = {"CampoSelector", "", "valor", "", "label", "icono", "Landroidx/compose/ui/graphics/vector/ImageVector;", "esError", "", "errorMsg", "onClick", "Lkotlin/Function0;", "CampoTextoNormal", "onValueChange", "Lkotlin/Function1;", "FormularioCita", "logica", "Lcom/example/psique_simple/logica/CitasViewModel;", "onVolver", "app_release"})
public final class FormularioCitaKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void FormularioCita(@org.jetbrains.annotations.NotNull()
    com.example.psique_simple.logica.CitasViewModel logica, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onVolver) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CampoTextoNormal(@org.jetbrains.annotations.NotNull()
    java.lang.String valor, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange, @org.jetbrains.annotations.NotNull()
    java.lang.String label, boolean esError, @org.jetbrains.annotations.NotNull()
    java.lang.String errorMsg) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CampoSelector(@org.jetbrains.annotations.NotNull()
    java.lang.String valor, @org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icono, boolean esError, @org.jetbrains.annotations.NotNull()
    java.lang.String errorMsg, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}