/* 1. Tabla de Cursos (Independiente) */
CREATE TABLE cursos (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL,
                        categoria VARCHAR(100) NOT NULL
);

/* 2. Tabla de Perfiles (Independiente) */
CREATE TABLE perfiles (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL UNIQUE
);

/* 3. Tabla de Usuarios */
CREATE TABLE usuarios (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          correo_electronico VARCHAR(100) NOT NULL UNIQUE,
                          contrasena VARCHAR(300) NOT NULL
);

/* 4. Tabla Intermedia Usuarios-Perfiles (Relación Many-to-Many) */
CREATE TABLE usuarios_perfiles (
                                   usuario_id BIGINT NOT NULL,
                                   perfil_id BIGINT NOT NULL,

                                   PRIMARY KEY(usuario_id, perfil_id),

                                   CONSTRAINT fk_usuarios_perfiles_usuario_id FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
                                   CONSTRAINT fk_usuarios_perfiles_perfil_id FOREIGN KEY(perfil_id) REFERENCES perfiles(id)
);

/* 5. Tabla de Tópicos */
CREATE TABLE topicos (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         titulo VARCHAR(100) NOT NULL,
                         mensaje VARCHAR(255) NOT NULL,
                         fecha_creacion DATETIME NOT NULL,
                         status VARCHAR(50) NOT NULL,
                         autor_id BIGINT NOT NULL,
                         curso_id BIGINT NOT NULL,

                         CONSTRAINT fk_topicos_autor_id FOREIGN KEY(autor_id) REFERENCES usuarios(id),
                         CONSTRAINT fk_topicos_curso_id FOREIGN KEY(curso_id) REFERENCES cursos(id),

    /* REGLA DE NEGOCIO: No permitir duplicados de título y mensaje */
                         CONSTRAINT uk_topicos_titulo_mensaje UNIQUE(titulo, mensaje)
);

/* 6. Tabla de Respuestas */
CREATE TABLE respuestas (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            mensaje VARCHAR(255) NOT NULL,
                            topico_id BIGINT NOT NULL,
                            fecha_creacion DATETIME NOT NULL,
                            autor_id BIGINT NOT NULL,
                            solucion TINYINT(1) DEFAULT 0, /* Booleano en MySQL */

                            CONSTRAINT fk_respuestas_topico_id FOREIGN KEY(topico_id) REFERENCES topicos(id) ON DELETE CASCADE,
                            CONSTRAINT fk_respuestas_autor_id FOREIGN KEY(autor_id) REFERENCES usuarios(id)
);