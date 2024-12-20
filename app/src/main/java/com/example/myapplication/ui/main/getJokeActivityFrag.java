package com.example.myapplication.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentGetJokeActivityBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class getJokeActivityFrag extends Fragment {
    FragmentGetJokeActivityBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGetJokeActivityBinding.inflate(inflater,container,false);
        binding.btnGetJoke.setOnClickListener(this:: fnGetJoke);
        return binding.getRoot();
    }

    private void fnGetJoke(View view){
        String url = "https://official-joke-api.appspot.com/random_joke";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    binding.txtViewJokeType.setText(jsonObject.getString("type"));
                    binding.txtViewSetup.setText(jsonObject.getString("setup"));
                    binding.txtViewPunchline.setText(jsonObject.getString("punchline"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "unable to connect to the joke!" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}