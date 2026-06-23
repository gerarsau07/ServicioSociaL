package com.example.serviciosocial;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.serviciosocial.ui.AjustesFragment;
import com.example.serviciosocial.ui.CalendarioFragment;
import com.example.serviciosocial.ui.CartaLiberacionFragment;
import com.example.serviciosocial.ui.ChatFragment;
import com.example.serviciosocial.ui.DocumentosFragment;
import com.example.serviciosocial.ui.EntidadesReceptorasFragment;
import com.example.serviciosocial.ui.InicioFragment;
import com.example.serviciosocial.ui.MiPerfilFragment;
import com.example.serviciosocial.ui.MisActividadesFragment;
import com.example.serviciosocial.ui.MisRequisitosFragment;
import com.example.serviciosocial.ui.NotificacionesFragment;
import com.example.serviciosocial.ui.ReportesAvanceFragment;
import com.example.serviciosocial.ui.SolicitarBajaFragment;
import com.example.serviciosocial.ui.SolicitudesFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        // Configuración para Edge-To-Edge (si está habilitado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Evento para el botón de menú
        toolbar.setNavigationOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        // Cargar el fragmento de Inicio por defecto
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new InicioFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_inicio);
        }

        // Evento para ítems del menú
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            Fragment fragment = null;

            if (id == R.id.nav_inicio) {
                fragment = new InicioFragment();
                toolbar.setTitle("Inicio");
            } else if (id == R.id.nav_mi_perfil) {
                fragment = new MiPerfilFragment();
                toolbar.setTitle("Mi Perfil");
            } else if (id == R.id.nav_mis_requisitos) {
                fragment = new MisRequisitosFragment();
                toolbar.setTitle("Mis Requisitos");
            } else if (id == R.id.nav_entidades_receptoras) {
                fragment = new EntidadesReceptorasFragment();
                toolbar.setTitle("Entidades Receptoras");
            } else if (id == R.id.nav_solicitudes) {
                fragment = new SolicitudesFragment();
                toolbar.setTitle("Solicitudes");
            } else if (id == R.id.nav_mis_actividades) {
                fragment = new MisActividadesFragment();
                toolbar.setTitle("Mis Actividades");
            } else if (id == R.id.nav_documentos) {
                fragment = new DocumentosFragment();
                toolbar.setTitle("Documentos");
            } else if (id == R.id.nav_notificaciones) {
                fragment = new NotificacionesFragment();
                toolbar.setTitle("Notificaciones");
            } else if (id == R.id.nav_reportes_avance) {
                fragment = new ReportesAvanceFragment();
                toolbar.setTitle("Reportes de Avance");
            } else if (id == R.id.nav_carta_liberacion) {
                fragment = new CartaLiberacionFragment();
                toolbar.setTitle("Carta de Liberación");
            } else if (id == R.id.nav_solicitar_baja) {
                fragment = new SolicitarBajaFragment();
                toolbar.setTitle("Solicitar Baja");
            } else if (id == R.id.nav_chat) {
                fragment = new ChatFragment();
                toolbar.setTitle("Chat");
            } else if (id == R.id.nav_calendario) {
                fragment = new CalendarioFragment();
                toolbar.setTitle("Calendario");
            } else if (id == R.id.nav_ajustes) {
                fragment = new AjustesFragment();
                toolbar.setTitle("Ajustes");
            } else if (id == R.id.nav_cerrar_sesion) {
                Toast.makeText(this, "Cerrar sesión", Toast.LENGTH_SHORT).show();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}