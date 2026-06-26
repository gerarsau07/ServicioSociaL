package com.example.serviciosocial.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.serviciosocial.R;

public class MiPerfilFragment extends Fragment {
    public MiPerfilFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mi_perfil, container, false);
        
        android.widget.TextView tvCreditos = view.findViewById(R.id.tv_creditos_perfil);
        if (tvCreditos != null) {
            android.content.SharedPreferences prefs = requireActivity().getSharedPreferences("MisCreditos", android.content.Context.MODE_PRIVATE);
            int creditos = prefs.getInt("verified_credits", 68); // 68 por defecto si no ha verificado
            tvCreditos.setText(creditos + "%");
        }
        
        return view;
    }
}
