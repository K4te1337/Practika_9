package com.example.myapplication.UI.View;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Context;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DATA.DataSources.Repositoriy;
import com.example.myapplication.R;
import com.example.myapplication.DATA.Model.FragmentNavigationData;

import java.io.File;

public class FirstFragment extends Fragment {
    private final int PERMISSION_REQUEST_CODE = 80;
    public FragmentNavigationData data = new FragmentNavigationData();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        TextView itemName = view.findViewById(R.id.text_view);
        /*Repositoriy repositoriy = new Repositoriy(this.getContext(), "file.txt", "SDFile");
        repositoriy.writeAppSpecDS("Кот не спит!!");
        itemName.setText(repositoriy.readAppSpecDS());
        if(!repositoriy.writeCommonFileDS("Кот уже не спит!")){
            Toast.makeText(getContext(), "Требуется разрешение на запись на карту памяти!", Toast.LENGTH_LONG).show();
            requestPermission();
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            repositoriy.writeCommonFileDS("Кот уже не спит!");
        }
        itemName.setText(repositoriy.readCommonFileDS());*/
        Repositoriy repositoriy = new Repositoriy();
        ImageView imageView = view.findViewById(R.id.image_view);
        repositoriy.createLocalds(this.getContext());
        repositoriy.setLocalName("Вот вам трехцветный кот!");
        repositoriy.setLocalImg(R.drawable.cat);
        itemName.setText(repositoriy.getItems().getName());
        imageView.setImageResource(repositoriy.getItems().getImage());

        Button toSecond = view.findViewById(R.id.to_second);
        toSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment);
                Bundle bundle = new Bundle();
                bundle.putString("res", data.getDataFrom1to2());
                Navigation.findNavController(view).navigate(R.id.SecondFragment, bundle);
            }
        });
        return view;
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getContext(), "Требуется разрешение на запись на карту памяти!", Toast.LENGTH_LONG).show();
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            Log.e("value", "Разрешение есть.");
        }
    }
}
