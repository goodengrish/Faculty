CREATE TABLE Utilisateur (
    id integer NOT NULL PRIMARY KEY,
    pseudo varchar(20)  NOT NULL,
    mdp varchar(50)  NOT NULL,
    etatCivil integer NOT NULL,
	carteBanc varchar(16) NOT NULL
);