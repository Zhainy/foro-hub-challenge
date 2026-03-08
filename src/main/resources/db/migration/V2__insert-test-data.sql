/* 1. Insertar un Curso por defecto (Para que al crear un Tópico no de error) */
INSERT INTO cursos (id, nombre, categoria)
VALUES (1, 'Spring Boot 3', 'Programación');

/* 2. Insertar un Perfil (Rol) de Administrador */
INSERT INTO perfiles (id, nombre)
VALUES (1, 'ROLE_ADMIN');

/* 3. Insertar el Usuario ADMIN
   Nota: La contraseña es "123456" hasheada con BCrypt.
   Hash: $2a$10$4nPk/g81euJjqAFMoPIBkuOtu9I.WM4knB6rJ4Ll0HZa6BYODMskK
*/
INSERT INTO usuarios (id, nombre, correo_electronico, contrasena)
VALUES (1, 'Admin', 'admin@forohub.com', '$2a$10$4nPk/g81euJjqAFMoPIBkuOtu9I.WM4knB6rJ4Ll0HZa6BYODMskK');

/* 4. Asociar el Usuario con el Perfil */
INSERT INTO usuarios_perfiles (usuario_id, perfil_id)
VALUES (1, 1);