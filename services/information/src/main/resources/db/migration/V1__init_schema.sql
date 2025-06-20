-- 1. Fonction Haversine pour calculer la distance (en km) entre deux points (lat/lon en degrés)
CREATE OR REPLACE FUNCTION haversine(
  lat1 DOUBLE PRECISION, lon1 DOUBLE PRECISION,
  lat2 DOUBLE PRECISION, lon2 DOUBLE PRECISION
) RETURNS DOUBLE PRECISION AS $$
SELECT 6371 * acos(
        cos(radians($1)) * cos(radians($3)) *
        cos(radians($4) - radians($2)) +
        sin(radians($1)) * sin(radians($3))
              );
$$ LANGUAGE SQL IMMUTABLE STRICT;


-- 2. Table des villes
CREATE TABLE IF NOT EXISTS city (
                       id         SERIAL PRIMARY KEY,
                       name       VARCHAR(100) NOT NULL,
                       latitude   DOUBLE PRECISION NOT NULL,
                       longitude  DOUBLE PRECISION NOT NULL
);

-- 3. Table des points d’intérêt
CREATE TABLE IF NOT EXISTS point_of_interest (
                           id                      SERIAL PRIMARY KEY,
                           city_id                 INTEGER NOT NULL
                                                        REFERENCES city(id)
                                                        ON DELETE CASCADE,
                           name                    VARCHAR(150) NOT NULL,
                           latitude                DOUBLE PRECISION NOT NULL,
                           longitude               DOUBLE PRECISION NOT NULL,
                           description_mongo_id    CHAR(24) NOT NULL
);

-- 4. Table des activités liées à un POI
CREATE TABLE IF NOT EXISTS activity (
                          id                    SERIAL PRIMARY KEY,
                          poi_id                INTEGER NOT NULL
                                                    REFERENCES point_of_interest(id)
                                                    ON DELETE CASCADE,
                          name                  VARCHAR(150) NOT NULL,
                          price                 DOUBLE PRECISION NOT NULL,
                          duration              VARCHAR(50),
                          description_mongo_id  CHAR(24) NOT NULL
);

-- 5. Table des hébergements d’une ville
CREATE TABLE IF NOT EXISTS lodging (
                             id                 SERIAL PRIMARY KEY,
                             city_id            INTEGER NOT NULL
                                                    REFERENCES city(id)
                                                    ON DELETE CASCADE,
                             name               VARCHAR(150) NOT NULL,
                             address            TEXT,
                             price_per_night    NUMERIC(8,2),
                             latitude           DOUBLE PRECISION,
                             longitude          DOUBLE PRECISION
);
