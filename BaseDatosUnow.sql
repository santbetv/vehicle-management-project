--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.18
-- Dumped by pg_dump version 12.18 (Ubuntu 12.18-0ubuntu0.20.04.1)

-- Started on 2024-06-27 16:43:24 -05

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

--
-- TOC entry 182 (class 1259 OID 59951)
-- Name: vehiculos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehiculos (
    fecha date,
    modelo smallint,
    id_vehiculo bigint NOT NULL,
    color character varying(255),
    marca character varying(255),
    matricula character varying(255)
);


ALTER TABLE public.vehiculos OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 59949)
-- Name: vehiculos_id_vehiculo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vehiculos_id_vehiculo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vehiculos_id_vehiculo_seq OWNER TO postgres;

--
-- TOC entry 2185 (class 0 OID 0)
-- Dependencies: 181
-- Name: vehiculos_id_vehiculo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehiculos_id_vehiculo_seq OWNED BY public.vehiculos.id_vehiculo;


--
-- TOC entry 2060 (class 2604 OID 59954)
-- Name: vehiculos id_vehiculo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehiculos ALTER COLUMN id_vehiculo SET DEFAULT nextval('public.vehiculos_id_vehiculo_seq'::regclass);


--
-- TOC entry 2178 (class 0 OID 59951)
-- Dependencies: 182
-- Data for Name: vehiculos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vehiculos (fecha, modelo, id_vehiculo, color, marca, matricula) FROM stdin;
1990-03-12	2013	1	Azul perlado	Chevrolet	0000GXP
2016-05-15	2016	2	Rojo	Ford	1234BCD
2019-09-20	2019	3	Negro	Renault	5678EFG
2018-12-05	2018	4	Blanco	Peugeot	9101HIJ
2020-07-11	2020	5	Gris	Volkswagen	1213KLM
2017-03-25	2017	6	Azul	BMW	1415NOP
2021-01-30	2014	7	Verde	Audi	1617QRS
2015-10-22	2015	8	Amarillo	Mercedes	1819TUV
2014-06-18	2014	9	Naranja	Toyota	2021WXY
2012-08-09	2012	10	Violeta	Nissan	2223ZAB
2013-11-05	2013	11	Marrón	Kia	2425CDE
2011-02-14	2011	12	Plateado	Hyundai	2627FGH
2018-09-19	2018	13	Dorado	Hyundai	2829IJK
2016-04-07	2016	14	Beige	Opel	3031LMN
2019-11-12	2019	15	Turquesa	Mazda	3233OPQ
2017-05-27	2017	16	Fucsia	Honda	3435RST
\.


--
-- TOC entry 2186 (class 0 OID 0)
-- Dependencies: 181
-- Name: vehiculos_id_vehiculo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vehiculos_id_vehiculo_seq', 16, true);


--
-- TOC entry 2062 (class 2606 OID 59959)
-- Name: vehiculos vehiculos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehiculos
    ADD CONSTRAINT vehiculos_pkey PRIMARY KEY (id_vehiculo);


--
-- TOC entry 2184 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2024-06-27 16:43:24 -05

--
-- PostgreSQL database dump complete
--

