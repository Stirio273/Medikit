-- Création de la base de données
CREATE DATABASE IF NOT EXISTS medikit;

-- Table users
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table patients
CREATE TABLE patients (
    patient_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    naissance DATE,
    genre VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255),
    adresse TEXT,
    contact_urgence VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_nom (nom),
    INDEX idx_prenom (prenom),
    INDEX idx_email (email)
);

-- Table medical_histories (antecedants)
CREATE TABLE medical_histories (
    history_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    diagnosis_date DATE,
    severity VARCHAR(50),
    status VARCHAR(50),
    patient_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    INDEX idx_patient_id (patient_id),
    INDEX idx_type (type)
);

-- Table attachments (radio, echographie)
CREATE TABLE attachments (
    attachment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    filename VARCHAR(255) NOT NULL,
    file_type VARCHAR(100) NOT NULL,
    file_size BIGINT,
    description TEXT,
    storage_path VARCHAR(500) NOT NULL,
    patient_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    INDEX idx_patient_id (patient_id),
    INDEX idx_file_type (file_type)
);

-- Table consultations
CREATE TABLE consultations (
    consultation_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    consultation_date TIMESTAMP,
    reason TEXT NOT NULL,
    observations TEXT,
    diagnosis TEXT,
    treatment_plan TEXT,
    patient_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    INDEX idx_patient_id (patient_id),
    INDEX idx_user_id (user_id),
    INDEX idx_consultation_date (consultation_date)
);

-- Table prescriptions
CREATE TABLE prescriptions (
    prescription_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    medication VARCHAR(255) NOT NULL,
    dosage VARCHAR(100) NOT NULL,
    frequency VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE,
    instructions TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    consultation_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (consultation_id) REFERENCES consultations(consultation_id) ON DELETE CASCADE,
    INDEX idx_consultation_id (consultation_id),
    INDEX idx_medication (medication),
    INDEX idx_is_active (is_active)
);

