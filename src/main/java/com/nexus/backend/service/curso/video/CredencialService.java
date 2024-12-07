package com.nexus.backend.service.curso.video;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CredencialService {
    private static final String CLIENT_SECRETS= "C:\\Users\\aaman\\Downloads\\secret.json";
    private static final Collection<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/youtube.upload");
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public Credential autorizarYoutube() throws Exception {
        InputStream in = new FileInputStream(CLIENT_SECRETS);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
                .setAccessType("offline")
                .build();

        Credential credential = flow.loadCredential("user");

        if (credential != null && credential.getRefreshToken() != null) return credential;

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(5000).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}
