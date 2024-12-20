package com.example.myapplication.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
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
import com.example.myapplication.databinding.FragmentGetUniversityBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getUniversityFrag extends Fragment {

    FragmentGetUniversityBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGetUniversityBinding.inflate(inflater, container, false);
        binding.btnSearchU.setOnClickListener(this::fnSearchUni);
        return binding.getRoot();
    }

    private void fnSearchUni(View view) {
        String strUrl = "http://universities.hipolabs.com/search?country=" + binding.edtFindUniversity.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    StringBuilder result = new StringBuilder();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject university = jsonArray.getJSONObject(i);

                        String name = university.getString("name");
                        JSONArray webPages = university.getJSONArray("web_pages");

                        String webPage = webPages.length() > 0 ? webPages.getString(0) : "No webpage available";

                        result.append("Name: ").append(name).append("\n")
                                .append("Webpage: ").append(webPage).append("\n\n");
                    }

                    binding.results.setText(result.toString());
                    binding.results.setMovementMethod(new ScrollingMovementMethod());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Unable to connect to the university list!" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);

    }
}