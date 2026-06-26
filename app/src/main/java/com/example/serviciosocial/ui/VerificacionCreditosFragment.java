package com.example.serviciosocial.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.serviciosocial.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Random;

public class VerificacionCreditosFragment extends Fragment {

    private MaterialButton btnUpload;
    private TextView tvFilename;
    private LinearLayout containerLoading;
    private LinearLayout containerResult;
    private CircularProgressIndicator circularProgress;
    private TextView tvPercentage;
    private TextView tvResultTitle;
    private TextView tvResultSubtitle;
    private MaterialButton btnContinue;

    private ActivityResultLauncher<Intent> filePickerLauncher;

    public VerificacionCreditosFragment() {
        // Constructor público vacío requerido
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar el launcher para seleccionar archivos
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        if (uri != null) {
                            String fileName = getFileName(uri);
                            tvFilename.setText("Archivo: " + fileName);
                            tvFilename.setVisibility(View.VISIBLE);
                            
                            // Iniciar simulación de análisis
                            startAnalysisSimulation();
                        }
                    }
                }
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_verificacion_creditos, container, false);

        // Inicializar vistas
        btnUpload = view.findViewById(R.id.btn_upload);
        tvFilename = view.findViewById(R.id.tv_filename);
        containerLoading = view.findViewById(R.id.container_loading);
        containerResult = view.findViewById(R.id.container_result);
        circularProgress = view.findViewById(R.id.circular_progress);
        tvPercentage = view.findViewById(R.id.tv_percentage);
        tvResultTitle = view.findViewById(R.id.tv_result_title);
        tvResultSubtitle = view.findViewById(R.id.tv_result_subtitle);
        btnContinue = view.findViewById(R.id.btn_continue);

        // Configurar eventos
        btnUpload.setOnClickListener(v -> openFilePicker());
        
        btnContinue.setOnClickListener(v -> {
            // Guardar en SharedPreferences
            int creditosParaGuardar = circularProgress.getProgress();
            android.content.SharedPreferences prefs = requireActivity().getSharedPreferences("MisCreditos", android.content.Context.MODE_PRIVATE);
            prefs.edit().putInt("verified_credits", creditosParaGuardar).apply();

            Toast.makeText(requireContext(), "Créditos guardados: " + creditosParaGuardar + "%", Toast.LENGTH_SHORT).show();
            
            // Regresar al fragmento anterior
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        String[] mimeTypes = {"application/pdf", "image/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        filePickerLauncher.launch(intent);
    }

    private void startAnalysisSimulation() {
        // Ocultar resultados previos si los hay
        containerResult.setVisibility(View.GONE);
        btnUpload.setEnabled(false);
        
        // Mostrar cargando
        containerLoading.setVisibility(View.VISIBLE);

        // Simular retraso de 3 segundos
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            containerLoading.setVisibility(View.GONE);
            btnUpload.setEnabled(true);
            
            // Generar porcentaje aleatorio entre 40 y 100
            int simulatedCredits = new Random().nextInt(61) + 40;
            showResults(simulatedCredits);
            
        }, 3000);
    }

    private void showResults(int percentage) {
        containerResult.setVisibility(View.VISIBLE);
        circularProgress.setProgress(percentage, true); // Animado
        tvPercentage.setText(percentage + "%");

        boolean hasEnoughCredits = percentage >= 60;

        if (hasEnoughCredits) {
            circularProgress.setIndicatorColor(ContextCompat.getColor(requireContext(), R.color.upp_success));
            tvResultTitle.setText("¡Cumples con el requisito!");
            tvResultTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.upp_success));
            tvResultSubtitle.setText("Puedes iniciar tu trámite de Servicio Social.");
            
            btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.upp_success));
            btnContinue.setEnabled(true);
        } else {
            circularProgress.setIndicatorColor(ContextCompat.getColor(requireContext(), R.color.upp_warning));
            tvResultTitle.setText("Créditos insuficientes");
            tvResultTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.upp_warning));
            tvResultSubtitle.setText("Necesitas al menos el 60% de los créditos para continuar.");
            
            btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.text_grey));
            btnContinue.setEnabled(false);
        }
    }

    // Método auxiliar para obtener el nombre del archivo desde la URI
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
