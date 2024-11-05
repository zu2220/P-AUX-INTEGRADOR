DECLARE @fecha DATE = '2024-11-04'; -- Fecha específica
DECLARE @nombre_ambiente NVARCHAR(50) = 'tina 1';
DECLARE @hora_apertura TIME = '08:00:00';
DECLARE @hora_cierre TIME;

-- Ajustar hora de cierre según el día de la semana
SET @hora_cierre = CASE 
                    WHEN DATEPART(WEEKDAY, @fecha) IN (2, 3, 4, 5, 6) THEN '20:00:00'
                    WHEN DATEPART(WEEKDAY, @fecha) = 7 THEN '18:00:00'
                    ELSE '20:00:00' 
                   END;

-- Obtener los servicios agendados para el ambiente y fecha dados
;WITH ServiciosEnFecha AS (
    SELECT hora_inicio, hora_fin
    FROM ServiciosSolicitados
    WHERE fecha_servicio = @fecha
      AND nombre_ambiente = @nombre_ambiente
),
Disponibilidad AS (
    -- Intervalo antes del primer servicio
    SELECT @hora_apertura AS inicio_disponible,
           MIN(hora_inicio) AS fin_disponible
    FROM ServiciosEnFecha
    WHERE hora_inicio > @hora_apertura
    
    UNION ALL

    -- Intervalos entre los servicios
    SELECT s1.hora_fin AS inicio_disponible,
           MIN(s2.hora_inicio) AS fin_disponible
    FROM ServiciosEnFecha s1
    JOIN ServiciosEnFecha s2 ON s1.hora_fin < s2.hora_inicio
    GROUP BY s1.hora_fin

    UNION ALL

    -- Intervalo después del último servicio
    SELECT (SELECT MAX(hora_fin) FROM ServiciosEnFecha) AS inicio_disponible,
           @hora_cierre AS fin_disponible
    WHERE (SELECT MAX(hora_fin) FROM ServiciosEnFecha) < @hora_cierre
)
SELECT inicio_disponible, fin_disponible
FROM Disponibilidad
WHERE inicio_disponible < fin_disponible
  AND DATEDIFF(HOUR, fin_disponible, @hora_cierre) >= 2 -- Restringir al margen antes del cierre
ORDER BY inicio_disponible;

