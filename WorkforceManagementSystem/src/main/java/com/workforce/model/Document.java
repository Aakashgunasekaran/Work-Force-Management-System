package com.workforce.model;

import java.time.LocalDate;

public class Document {
    private static int nextDocId = 1;

    private final int documentId;
    private String empID;
    private String documentType;
    private String fileName;
    private LocalDate uploadDate;

    public Document(String empID, String documentType, String fileName) {
        this.documentId = nextDocId++;
        this.empID = empID;
        this.documentType = documentType;
        this.fileName = fileName;
        this.uploadDate = null;
    }

    public int getDocumentId() { return documentId; }
    public String getEmpID() { return empID; }
    public String getFileName() { return fileName; }

    public void uploadDocument() {
        this.uploadDate = LocalDate.now();
        System.out.println("Document '" + fileName + "' (" + documentType + ") uploaded for emp "
                + empID + " on " + uploadDate + ".");
    }

    public void viewDocument() {
        System.out.println("---------------------------------");
        System.out.println("Document ID : " + documentId);
        System.out.println("Emp ID      : " + empID);
        System.out.println("Type        : " + documentType);
        System.out.println("File Name   : " + fileName);
        System.out.println("Upload Date : " + (uploadDate != null ? uploadDate : "Not uploaded yet"));
        System.out.println("---------------------------------");
    }

    public boolean deleteDocument() {
        System.out.println("Document '" + fileName + "' deleted.");
        return true;
    }
}
