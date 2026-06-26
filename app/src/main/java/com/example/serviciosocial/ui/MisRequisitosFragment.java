package com.example.serviciosocial.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.serviciosocial.R;

public class MisRequisitosFragment extends Fragment {
    public MisRequisitosFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_requisitos, container, false);
        
        android.widget.TextView tvCreditos = view.findViewById(R.id.tv_mis_requisitos_creditos);
        android.widget.TextView tvCursados = view.findViewById(R.id.tv_mis_requisitos_cursados);
        com.google.android.material.progressindicator.CircularProgressIndicator progress = view.findViewById(R.id.progress_mis_requisitos);
        
        android.content.SharedPreferences prefs = requireActivity().getSharedPreferences("MisCreditos", android.content.Context.MODE_PRIVATE);
        int creditos = prefs.getInt("verified_credits", 0);
        
        if (tvCreditos != null && tvCursados != null && progress != null) {
            tvCreditos.setText(creditos + "%");
            tvCursados.setText(creditos + "%");
            progress.setProgress(creditos);
        }

        com.google.android.material.button.MaterialButton btnVerificar = view.findViewById(R.id.btn_verificar_creditos);
        if (btnVerificar != null) {
            btnVerificar.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new VerificacionCreditosFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }
        
        return view;
    }
}
