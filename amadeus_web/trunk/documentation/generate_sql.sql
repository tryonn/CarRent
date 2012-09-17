--
-- PostgreSQL database dump
--

-- Started on 2007-01-29 15:46:37 E. South America Standard Time

SET client_encoding = 'UTF8';
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 1794 (class 1262 OID 17144)
-- Name: amadeus; Type: DATABASE; Schema: -; Owner: amadeus
--

CREATE DATABASE amadeus WITH TEMPLATE = template0 ENCODING = 'UTF8';


ALTER DATABASE amadeus OWNER TO amadeus;

\connect amadeus

SET client_encoding = 'UTF8';
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 1795 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1297 (class 1259 OID 99614)
-- Dependencies: 5
-- Name: _column; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE _column (
    sqcolumn integer NOT NULL,
    sqcolumnmatching integer,
    ds_term character varying NOT NULL,
    id_order character(1) NOT NULL,
    ds_sentence character varying NOT NULL,
    ds_position integer
);


ALTER TABLE public._column OWNER TO amadeus;

--
-- TOC entry 1299 (class 1259 OID 99638)
-- Dependencies: 5
-- Name: alternative; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE alternative (
    sqalternative integer NOT NULL,
    sqmultiplechoice integer,
    ds_description character varying NOT NULL,
    in_correct boolean NOT NULL,
    ds_order character(1) NOT NULL,
    ds_position integer
);


ALTER TABLE public.alternative OWNER TO amadeus;

--
-- TOC entry 1255 (class 1259 OID 17145)
-- Dependencies: 5
-- Name: amadeususer; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE amadeususer (
    squser integer NOT NULL,
    "login" character varying(20) NOT NULL,
    "password" character varying(20) NOT NULL,
    sqperson integer NOT NULL,
    sqprofile integer NOT NULL
);


ALTER TABLE public.amadeususer OWNER TO amadeus;

--
-- TOC entry 1256 (class 1259 OID 17147)
-- Dependencies: 5
-- Name: answer; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE answer (
    sqanswer integer NOT NULL,
    sqperson integer NOT NULL,
    sqpoll integer NOT NULL,
    dt_answer date NOT NULL,
    sqchoice integer NOT NULL,
    ds_position integer
);


ALTER TABLE public.answer OWNER TO amadeus;

--
-- TOC entry 1295 (class 1259 OID 99593)
-- Dependencies: 5
-- Name: blank; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE blank (
    sqblank integer NOT NULL,
    sqblanksfilling integer NOT NULL,
    ds_word character varying NOT NULL,
    ds_position integer
);


ALTER TABLE public.blank OWNER TO amadeus;

--
-- TOC entry 1294 (class 1259 OID 99581)
-- Dependencies: 5
-- Name: blanksfilling; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE blanksfilling (
    sqblanksfilling integer NOT NULL,
    ds_text text NOT NULL
);


ALTER TABLE public.blanksfilling OWNER TO amadeus;

--
-- TOC entry 1257 (class 1259 OID 17149)
-- Dependencies: 1625 5
-- Name: choice; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE choice (
    sqchoice integer NOT NULL,
    sqpoll integer,
    ds_option character varying NOT NULL,
    ds_alternative character varying NOT NULL,
    qt_votes integer DEFAULT 0,
    ds_position integer
);


ALTER TABLE public.choice OWNER TO amadeus;

--
-- TOC entry 1292 (class 1259 OID 99563)
-- Dependencies: 5
-- Name: columnmatching; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE columnmatching (
    sqcolumnmatching integer NOT NULL,
    sqcolumn integer
);


ALTER TABLE public.columnmatching OWNER TO amadeus;

--
-- TOC entry 1283 (class 1259 OID 74900)
-- Dependencies: 5
-- Name: commentary; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE commentary (
    sqcommentary integer NOT NULL,
    sqcourseevaluation integer,
    ds_description text,
    ds_position bigint
);


ALTER TABLE public.commentary OWNER TO amadeus;

--
-- TOC entry 1258 (class 1259 OID 17155)
-- Dependencies: 5
-- Name: course; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE course (
    sqcourse numeric NOT NULL,
    nmcourse character varying(100) NOT NULL,
    sqperson numeric NOT NULL,
    dsobjectives text,
    dscontents text,
    qtmaxstudents integer,
    dtcreation date NOT NULL,
    dtregstart date NOT NULL,
    dtregend date NOT NULL,
    dtstart date NOT NULL,
    dtend date NOT NULL,
    dstargetpublic text
);


ALTER TABLE public.course OWNER TO amadeus;

--
-- TOC entry 1259 (class 1259 OID 17160)
-- Dependencies: 5
-- Name: course_keyword; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE course_keyword (
    sqkeyword integer NOT NULL,
    sqcourse integer NOT NULL
);


ALTER TABLE public.course_keyword OWNER TO amadeus;

--
-- TOC entry 1260 (class 1259 OID 17162)
-- Dependencies: 5
-- Name: course_module; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE course_module (
    sqmodule integer NOT NULL,
    sqcourse integer NOT NULL,
    ds_position integer
);


ALTER TABLE public.course_module OWNER TO amadeus;

--
-- TOC entry 1261 (class 1259 OID 17164)
-- Dependencies: 5
-- Name: course_tool; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE course_tool (
    sqcourse numeric NOT NULL,
    sqtool numeric NOT NULL
);


ALTER TABLE public.course_tool OWNER TO amadeus;

--
-- TOC entry 1282 (class 1259 OID 74891)
-- Dependencies: 5
-- Name: courseevaluation; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE courseevaluation (
    sqcourseevaluation integer NOT NULL,
    creationdate date
);


ALTER TABLE public.courseevaluation OWNER TO amadeus;

--
-- TOC entry 1284 (class 1259 OID 74912)
-- Dependencies: 5
-- Name: criterion; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE criterion (
    sqcriterion integer NOT NULL,
    sqcourseevaluation integer,
    nm_name character varying(100),
    ds_position bigint,
    constant integer
);


ALTER TABLE public.criterion OWNER TO amadeus;

--
-- TOC entry 1287 (class 1259 OID 74939)
-- Dependencies: 5
-- Name: criterionanswer; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE criterionanswer (
    sqcriterionanswer integer NOT NULL,
    sqevaluationanswer integer,
    in_r1 character(1),
    in_r2 character(1),
    in_r3 character(1),
    in_r4 character(1)
);


ALTER TABLE public.criterionanswer OWNER TO amadeus;

--
-- TOC entry 1262 (class 1259 OID 17169)
-- Dependencies: 5
-- Name: delivery; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE delivery (
    sqdelivery integer NOT NULL,
    sqhomework integer NOT NULL,
    sqperson integer NOT NULL,
    dt_date date NOT NULL,
    bbfile bytea NOT NULL
);


ALTER TABLE public.delivery OWNER TO amadeus;

--
-- TOC entry 1290 (class 1259 OID 99549)
-- Dependencies: 5
-- Name: discoursivequestion; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE discoursivequestion (
    sqdiscoursivequestion integer NOT NULL,
    ds_question character varying NOT NULL,
    id_level character(1) NOT NULL
);


ALTER TABLE public.discoursivequestion OWNER TO amadeus;

--
-- TOC entry 1289 (class 1259 OID 99519)
-- Dependencies: 5
-- Name: evaluation; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE evaluation (
    sqevaluation integer NOT NULL,
    sqmodule integer,
    nm_name character varying NOT NULL,
    dt_beginning date NOT NULL,
    dt_end date NOT NULL,
    id_weight double precision NOT NULL,
    id_maxscore double precision NOT NULL,
    "sqPerson" integer
);


ALTER TABLE public.evaluation OWNER TO amadeus;

--
-- TOC entry 1301 (class 1259 OID 99664)
-- Dependencies: 5
-- Name: evaluation_discoursive_question; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE evaluation_discoursive_question (
    sqdiscoursivequestion integer NOT NULL,
    sqevaluation integer NOT NULL,
    ds_position integer
);


ALTER TABLE public.evaluation_discoursive_question OWNER TO amadeus;

--
-- TOC entry 1300 (class 1259 OID 99650)
-- Dependencies: 5
-- Name: evaluation_objective_question; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE evaluation_objective_question (
    sqobjectivequestion integer NOT NULL,
    sqevaluation integer NOT NULL,
    ds_position integer
);


ALTER TABLE public.evaluation_objective_question OWNER TO amadeus;

--
-- TOC entry 1286 (class 1259 OID 74930)
-- Dependencies: 5
-- Name: evaluationanswer; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE evaluationanswer (
    sqevaluationanswer integer NOT NULL,
    sqcourseevaluation integer,
    ds_frequencytype character(1),
    sqcriterion bigint,
    sqcriterionanswer bigint
);


ALTER TABLE public.evaluationanswer OWNER TO amadeus;

--
-- TOC entry 1263 (class 1259 OID 17174)
-- Dependencies: 5
-- Name: forum; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE forum (
    sqforum integer NOT NULL,
    sqmodule integer,
    nm_forum character varying NOT NULL,
    ds_position integer,
    ds_forum text,
    dt_hora time without time zone,
    dt_data date
);


ALTER TABLE public.forum OWNER TO amadeus;

--
-- TOC entry 1264 (class 1259 OID 17179)
-- Dependencies: 5
-- Name: homework; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE homework (
    sqhomework integer NOT NULL,
    sqmodule integer,
    nm_homework character varying NOT NULL,
    dt_deliverydate date NOT NULL,
    in_deliverable boolean,
    ds_description text,
    dt_date date NOT NULL,
    ds_position integer
);


ALTER TABLE public.homework OWNER TO amadeus;

--
-- TOC entry 1265 (class 1259 OID 17184)
-- Dependencies: 5
-- Name: image; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE image (
    sqimage integer NOT NULL,
    bbphoto bytea NOT NULL,
    sqperson integer NOT NULL
);


ALTER TABLE public.image OWNER TO amadeus;

--
-- TOC entry 1303 (class 1259 OID 107886)
-- Dependencies: 5
-- Name: jogos; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE jogos (
    id_jogo serial NOT NULL,
    nm_jogo character varying(100) NOT NULL,
    minimo smallint NOT NULL,
    maximo smallint NOT NULL,
    url character varying(300) NOT NULL,
    flag smallint NOT NULL,
    id_curso smallint NOT NULL,
    comentario text
);


ALTER TABLE public.jogos OWNER TO amadeus;

--
-- TOC entry 1266 (class 1259 OID 17189)
-- Dependencies: 5
-- Name: keyword; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE keyword (
    sqkeyword integer NOT NULL,
    nmkeyword character varying NOT NULL,
    qt_popularity integer
);


ALTER TABLE public.keyword OWNER TO amadeus;

--
-- TOC entry 1267 (class 1259 OID 17194)
-- Dependencies: 5
-- Name: material; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE material (
    sqmaterial integer NOT NULL,
    sqmodule integer,
    sqperson integer NOT NULL,
    dt_postdate date NOT NULL,
    bb_file bytea,
    nm_material character varying(100) NOT NULL,
    ds_contenttype character varying(100) NOT NULL,
    ds_position integer
);


ALTER TABLE public.material OWNER TO amadeus;

--
-- TOC entry 1268 (class 1259 OID 17199)
-- Dependencies: 5
-- Name: message; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE message (
    sqmessage numeric NOT NULL,
    ds_title character varying NOT NULL,
    ds_body character varying NOT NULL,
    dt_date date NOT NULL,
    hr_hour time without time zone NOT NULL,
    sqforum integer,
    ds_position numeric,
    sqperson numeric NOT NULL
);


ALTER TABLE public.message OWNER TO amadeus;

--
-- TOC entry 1269 (class 1259 OID 17204)
-- Dependencies: 5
-- Name: module; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE module (
    sqmodule integer NOT NULL,
    nmmodule character varying NOT NULL,
    dsmodule character varying NOT NULL,
    in_visible boolean NOT NULL,
    dsorder integer NOT NULL,
    "in_hasCourseEval" boolean,
    sqcourseevaluation bigint
);


ALTER TABLE public.module OWNER TO amadeus;

--
-- TOC entry 1296 (class 1259 OID 99605)
-- Dependencies: 5
-- Name: multiplechoice; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE multiplechoice (
    sqmultiplechoice integer NOT NULL
);


ALTER TABLE public.multiplechoice OWNER TO amadeus;

--
-- TOC entry 1291 (class 1259 OID 99556)
-- Dependencies: 5
-- Name: objectivequestion; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE objectivequestion (
    sqobjectivequestion integer NOT NULL,
    ds_question character varying NOT NULL,
    id_level character(1) NOT NULL
);


ALTER TABLE public.objectivequestion OWNER TO amadeus;

--
-- TOC entry 1270 (class 1259 OID 17209)
-- Dependencies: 5
-- Name: permission; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE permission (
    sqpermission integer NOT NULL,
    nmpermission character varying(100) NOT NULL,
    idpermission integer
);


ALTER TABLE public.permission OWNER TO amadeus;

--
-- TOC entry 1271 (class 1259 OID 17211)
-- Dependencies: 5
-- Name: person; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE person (
    sqperson numeric NOT NULL,
    nmperson character varying(50) NOT NULL,
    dtbirth date,
    dssex character(1),
    email character varying(50),
    nucpf character varying(11),
    nuphone character varying(20),
    nmcity character varying(100),
    nmstate character varying(2)
);


ALTER TABLE public.person OWNER TO amadeus;

--
-- TOC entry 1272 (class 1259 OID 17216)
-- Dependencies: 5
-- Name: person_evaluate_request; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE person_evaluate_request (
    sqrequest integer NOT NULL,
    sqperson integer NOT NULL,
    dtdate_a date NOT NULL
);


ALTER TABLE public.person_evaluate_request OWNER TO amadeus;

--
-- TOC entry 1273 (class 1259 OID 17218)
-- Dependencies: 5
-- Name: person_role_course; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE person_role_course (
    sqcourse integer NOT NULL,
    sqperson integer NOT NULL,
    sqrole integer NOT NULL,
    dtdate date NOT NULL
);


ALTER TABLE public.person_role_course OWNER TO amadeus;

--
-- TOC entry 1288 (class 1259 OID 74948)
-- Dependencies: 5
-- Name: personevaluatecourse; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE personevaluatecourse (
    sqperson integer NOT NULL,
    sqcourseevaluation integer NOT NULL
);


ALTER TABLE public.personevaluatecourse OWNER TO amadeus;

--
-- TOC entry 1274 (class 1259 OID 17220)
-- Dependencies: 1626 5
-- Name: poll; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE poll (
    sqpoll integer NOT NULL,
    sqmodule integer,
    nm_poll character varying NOT NULL,
    ds_question character varying NOT NULL,
    dt_creationdate date NOT NULL,
    dt_finishdate date NOT NULL,
    qt_votes integer DEFAULT 0 NOT NULL,
    ds_position integer
);


ALTER TABLE public.poll OWNER TO amadeus;

--
-- TOC entry 1275 (class 1259 OID 17226)
-- Dependencies: 5
-- Name: profile; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE profile (
    sqprofile integer NOT NULL,
    nmprofile character varying(30) NOT NULL,
    inintern character(1) NOT NULL,
    idprofile integer
);


ALTER TABLE public.profile OWNER TO amadeus;

--
-- TOC entry 1276 (class 1259 OID 17228)
-- Dependencies: 5
-- Name: profile_permission; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE profile_permission (
    sqprofile integer NOT NULL,
    sqpermission integer NOT NULL
);


ALTER TABLE public.profile_permission OWNER TO amadeus;

--
-- TOC entry 1285 (class 1259 OID 74921)
-- Dependencies: 5
-- Name: question; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE question (
    sqquestion integer NOT NULL,
    sqcriterion integer,
    ds_description character varying(100),
    ds_order integer
);


ALTER TABLE public.question OWNER TO amadeus;

--
-- TOC entry 1277 (class 1259 OID 17230)
-- Dependencies: 1627 5
-- Name: request; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE request (
    sqrequest integer NOT NULL,
    sqperson integer NOT NULL,
    sqcourse integer,
    instatus character(2) DEFAULT 'ag'::bpchar NOT NULL,
    dtdate_s date,
    dsinterest text NOT NULL,
    in_teaching boolean NOT NULL
);


ALTER TABLE public.request OWNER TO amadeus;

--
-- TOC entry 1278 (class 1259 OID 17236)
-- Dependencies: 5
-- Name: resume; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE resume (
    sqresume integer NOT NULL,
    sqperson integer NOT NULL,
    dsdegree text,
    dtyear integer,
    nminstitution text,
    dsdescription text
);


ALTER TABLE public.resume OWNER TO amadeus;

--
-- TOC entry 1279 (class 1259 OID 17241)
-- Dependencies: 5
-- Name: role; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE "role" (
    sqrole integer NOT NULL,
    nmrole character varying(50) NOT NULL,
    idrole integer
);


ALTER TABLE public."role" OWNER TO amadeus;

--
-- TOC entry 1298 (class 1259 OID 99626)
-- Dependencies: 5
-- Name: sentence; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE sentence (
    sqsentence integer NOT NULL,
    sqtrueorfalse integer,
    ds_description character varying NOT NULL,
    in_value boolean NOT NULL,
    ds_order character(1) NOT NULL,
    ds_position integer
);


ALTER TABLE public.sentence OWNER TO amadeus;

--
-- TOC entry 1280 (class 1259 OID 17243)
-- Dependencies: 5
-- Name: tool; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE tool (
    sqtool numeric NOT NULL,
    nmtool character varying(100) NOT NULL,
    dstool text NOT NULL
);


ALTER TABLE public.tool OWNER TO amadeus;

--
-- TOC entry 1293 (class 1259 OID 99572)
-- Dependencies: 5
-- Name: trueorfalse; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE trueorfalse (
    sqtrueorfalse integer NOT NULL,
    sqsentence integer
);


ALTER TABLE public.trueorfalse OWNER TO amadeus;

--
-- TOC entry 1281 (class 1259 OID 17248)
-- Dependencies: 5
-- Name: user_profile_course; Type: TABLE; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE TABLE user_profile_course (
    sqcourse integer NOT NULL,
    sqprofile integer NOT NULL,
    squser integer NOT NULL,
    dtdate date NOT NULL
);


ALTER TABLE public.user_profile_course OWNER TO amadeus;

--
-- TOC entry 1728 (class 2606 OID 99620)
-- Dependencies: 1297 1297
-- Name: _column_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY _column
    ADD CONSTRAINT _column_pkey PRIMARY KEY (sqcolumn);


--
-- TOC entry 1732 (class 2606 OID 99644)
-- Dependencies: 1299 1299
-- Name: alternative_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY alternative
    ADD CONSTRAINT alternative_pkey PRIMARY KEY (sqalternative);


--
-- TOC entry 1630 (class 2606 OID 17251)
-- Dependencies: 1255 1255
-- Name: amadeus_user_login_key; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY amadeususer
    ADD CONSTRAINT amadeus_user_login_key UNIQUE ("login");


--
-- TOC entry 1632 (class 2606 OID 17253)
-- Dependencies: 1255 1255
-- Name: amadeus_user_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY amadeususer
    ADD CONSTRAINT amadeus_user_pkey PRIMARY KEY (squser);


--
-- TOC entry 1634 (class 2606 OID 17255)
-- Dependencies: 1256 1256
-- Name: answer_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY answer
    ADD CONSTRAINT answer_pkey PRIMARY KEY (sqanswer);


--
-- TOC entry 1724 (class 2606 OID 99599)
-- Dependencies: 1295 1295
-- Name: blank_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY blank
    ADD CONSTRAINT blank_pkey PRIMARY KEY (sqblank);


--
-- TOC entry 1722 (class 2606 OID 99587)
-- Dependencies: 1294 1294
-- Name: blanksfilling_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY blanksfilling
    ADD CONSTRAINT blanksfilling_pkey PRIMARY KEY (sqblanksfilling);


--
-- TOC entry 1738 (class 2606 OID 107890)
-- Dependencies: 1303 1303
-- Name: chave; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY jogos
    ADD CONSTRAINT chave PRIMARY KEY (id_jogo);


--
-- TOC entry 1637 (class 2606 OID 17257)
-- Dependencies: 1257 1257
-- Name: choice_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY choice
    ADD CONSTRAINT choice_pkey PRIMARY KEY (sqchoice);


--
-- TOC entry 1718 (class 2606 OID 99566)
-- Dependencies: 1292 1292
-- Name: columnmatching_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY columnmatching
    ADD CONSTRAINT columnmatching_pkey PRIMARY KEY (sqcolumnmatching);


--
-- TOC entry 1697 (class 2606 OID 74906)
-- Dependencies: 1283 1283
-- Name: commentary_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY commentary
    ADD CONSTRAINT commentary_pkey PRIMARY KEY (sqcommentary);


--
-- TOC entry 1641 (class 2606 OID 17259)
-- Dependencies: 1259 1259 1259
-- Name: course_keyword_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY course_keyword
    ADD CONSTRAINT course_keyword_pkey PRIMARY KEY (sqkeyword, sqcourse);


--
-- TOC entry 1643 (class 2606 OID 17261)
-- Dependencies: 1260 1260 1260
-- Name: course_module_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY course_module
    ADD CONSTRAINT course_module_pkey PRIMARY KEY (sqmodule, sqcourse);


--
-- TOC entry 1639 (class 2606 OID 17263)
-- Dependencies: 1258 1258
-- Name: course_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY course
    ADD CONSTRAINT course_pkey PRIMARY KEY (sqcourse);


--
-- TOC entry 1645 (class 2606 OID 17265)
-- Dependencies: 1261 1261 1261
-- Name: course_tool_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY course_tool
    ADD CONSTRAINT course_tool_pkey PRIMARY KEY (sqcourse, sqtool);


--
-- TOC entry 1695 (class 2606 OID 74894)
-- Dependencies: 1282 1282
-- Name: courseevaluation_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY courseevaluation
    ADD CONSTRAINT courseevaluation_pkey PRIMARY KEY (sqcourseevaluation);


--
-- TOC entry 1699 (class 2606 OID 74915)
-- Dependencies: 1284 1284
-- Name: criterion_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY criterion
    ADD CONSTRAINT criterion_pkey PRIMARY KEY (sqcriterion);


--
-- TOC entry 1707 (class 2606 OID 74942)
-- Dependencies: 1287 1287
-- Name: criterionanswer_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY criterionanswer
    ADD CONSTRAINT criterionanswer_pkey PRIMARY KEY (sqcriterionanswer);


--
-- TOC entry 1647 (class 2606 OID 17267)
-- Dependencies: 1262 1262
-- Name: delivery_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY delivery
    ADD CONSTRAINT delivery_pkey PRIMARY KEY (sqdelivery);


--
-- TOC entry 1736 (class 2606 OID 99667)
-- Dependencies: 1301 1301 1301
-- Name: discoursive_evaluation_question_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY evaluation_discoursive_question
    ADD CONSTRAINT discoursive_evaluation_question_pkey PRIMARY KEY (sqdiscoursivequestion, sqevaluation);


--
-- TOC entry 1714 (class 2606 OID 99555)
-- Dependencies: 1290 1290
-- Name: discoursivequestion_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY discoursivequestion
    ADD CONSTRAINT discoursivequestion_pkey PRIMARY KEY (sqdiscoursivequestion);


--
-- TOC entry 1711 (class 2606 OID 99525)
-- Dependencies: 1289 1289
-- Name: evaluation_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY evaluation
    ADD CONSTRAINT evaluation_pkey PRIMARY KEY (sqevaluation);


--
-- TOC entry 1703 (class 2606 OID 74933)
-- Dependencies: 1286 1286
-- Name: evaluationanswer_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY evaluationanswer
    ADD CONSTRAINT evaluationanswer_pkey PRIMARY KEY (sqevaluationanswer);


--
-- TOC entry 1649 (class 2606 OID 17269)
-- Dependencies: 1263 1263
-- Name: forum_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY forum
    ADD CONSTRAINT forum_pkey PRIMARY KEY (sqforum);


--
-- TOC entry 1653 (class 2606 OID 17271)
-- Dependencies: 1265 1265
-- Name: image_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY image
    ADD CONSTRAINT image_pkey PRIMARY KEY (sqimage);


--
-- TOC entry 1655 (class 2606 OID 17273)
-- Dependencies: 1266 1266
-- Name: keyword_name_key; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY keyword
    ADD CONSTRAINT keyword_name_key UNIQUE (nmkeyword);


--
-- TOC entry 1657 (class 2606 OID 17275)
-- Dependencies: 1266 1266
-- Name: keyword_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY keyword
    ADD CONSTRAINT keyword_pkey PRIMARY KEY (sqkeyword);


--
-- TOC entry 1659 (class 2606 OID 17277)
-- Dependencies: 1267 1267
-- Name: material_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY material
    ADD CONSTRAINT material_pkey PRIMARY KEY (sqmaterial);


--
-- TOC entry 1651 (class 2606 OID 17279)
-- Dependencies: 1264 1264
-- Name: materialdelivery_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY homework
    ADD CONSTRAINT materialdelivery_pkey PRIMARY KEY (sqhomework);


--
-- TOC entry 1662 (class 2606 OID 17281)
-- Dependencies: 1268 1268
-- Name: message_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY message
    ADD CONSTRAINT message_pkey PRIMARY KEY (sqmessage);


--
-- TOC entry 1665 (class 2606 OID 17283)
-- Dependencies: 1269 1269
-- Name: module_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY module
    ADD CONSTRAINT module_pkey PRIMARY KEY (sqmodule);


--
-- TOC entry 1726 (class 2606 OID 99608)
-- Dependencies: 1296 1296
-- Name: multiplechoice_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY multiplechoice
    ADD CONSTRAINT multiplechoice_pkey PRIMARY KEY (sqmultiplechoice);


--
-- TOC entry 1734 (class 2606 OID 99653)
-- Dependencies: 1300 1300 1300
-- Name: objective_evaluation_question_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY evaluation_objective_question
    ADD CONSTRAINT objective_evaluation_question_pkey PRIMARY KEY (sqobjectivequestion, sqevaluation);


--
-- TOC entry 1716 (class 2606 OID 99562)
-- Dependencies: 1291 1291
-- Name: objectivequestion_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY objectivequestion
    ADD CONSTRAINT objectivequestion_pkey PRIMARY KEY (sqobjectivequestion);


--
-- TOC entry 1667 (class 2606 OID 17285)
-- Dependencies: 1270 1270
-- Name: permission_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (sqpermission);


--
-- TOC entry 1671 (class 2606 OID 17287)
-- Dependencies: 1272 1272 1272
-- Name: person_evaluate_request_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY person_evaluate_request
    ADD CONSTRAINT person_evaluate_request_pkey PRIMARY KEY (sqrequest, sqperson);


--
-- TOC entry 1669 (class 2606 OID 17289)
-- Dependencies: 1271 1271
-- Name: person_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (sqperson);


--
-- TOC entry 1673 (class 2606 OID 17291)
-- Dependencies: 1273 1273 1273 1273
-- Name: person_role_course_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY person_role_course
    ADD CONSTRAINT person_role_course_pkey PRIMARY KEY (sqcourse, sqperson, sqrole);


--
-- TOC entry 1709 (class 2606 OID 74951)
-- Dependencies: 1288 1288 1288
-- Name: personevaluatecourse_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY personevaluatecourse
    ADD CONSTRAINT personevaluatecourse_pkey PRIMARY KEY (sqperson, sqcourseevaluation);


--
-- TOC entry 1675 (class 2606 OID 17293)
-- Dependencies: 1274 1274
-- Name: poll_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_pkey PRIMARY KEY (sqpoll);


--
-- TOC entry 1677 (class 2606 OID 17295)
-- Dependencies: 1275 1275
-- Name: profile_idprofile_key; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY profile
    ADD CONSTRAINT profile_idprofile_key UNIQUE (idprofile);


--
-- TOC entry 1681 (class 2606 OID 17297)
-- Dependencies: 1276 1276 1276
-- Name: profile_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY profile_permission
    ADD CONSTRAINT profile_permission_pkey PRIMARY KEY (sqprofile, sqpermission);


--
-- TOC entry 1679 (class 2606 OID 17299)
-- Dependencies: 1275 1275
-- Name: profile_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY profile
    ADD CONSTRAINT profile_pkey PRIMARY KEY (sqprofile);


--
-- TOC entry 1701 (class 2606 OID 74924)
-- Dependencies: 1285 1285
-- Name: question_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY question
    ADD CONSTRAINT question_pkey PRIMARY KEY (sqquestion);


--
-- TOC entry 1683 (class 2606 OID 17301)
-- Dependencies: 1277 1277
-- Name: request_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY request
    ADD CONSTRAINT request_pkey PRIMARY KEY (sqrequest);


--
-- TOC entry 1685 (class 2606 OID 17303)
-- Dependencies: 1278 1278
-- Name: resume_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY resume
    ADD CONSTRAINT resume_pkey PRIMARY KEY (sqresume);


--
-- TOC entry 1687 (class 2606 OID 17305)
-- Dependencies: 1279 1279
-- Name: role_idrole_key; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY "role"
    ADD CONSTRAINT role_idrole_key UNIQUE (idrole);


--
-- TOC entry 1689 (class 2606 OID 17307)
-- Dependencies: 1279 1279
-- Name: role_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY "role"
    ADD CONSTRAINT role_pkey PRIMARY KEY (sqrole);


--
-- TOC entry 1730 (class 2606 OID 99632)
-- Dependencies: 1298 1298
-- Name: sentence_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY sentence
    ADD CONSTRAINT sentence_pkey PRIMARY KEY (sqsentence);


--
-- TOC entry 1691 (class 2606 OID 17309)
-- Dependencies: 1280 1280
-- Name: tool_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY tool
    ADD CONSTRAINT tool_pkey PRIMARY KEY (sqtool);


--
-- TOC entry 1720 (class 2606 OID 99575)
-- Dependencies: 1293 1293
-- Name: trueorfalse_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY trueorfalse
    ADD CONSTRAINT trueorfalse_pkey PRIMARY KEY (sqtrueorfalse);


--
-- TOC entry 1693 (class 2606 OID 17311)
-- Dependencies: 1281 1281 1281 1281
-- Name: user_profile_course_pkey; Type: CONSTRAINT; Schema: public; Owner: amadeus; Tablespace: 
--

ALTER TABLE ONLY user_profile_course
    ADD CONSTRAINT user_profile_course_pkey PRIMARY KEY (squser, sqprofile, sqcourse);


--
-- TOC entry 1712 (class 1259 OID 99683)
-- Dependencies: 1289
-- Name: fki_sqPerson; Type: INDEX; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE INDEX "fki_sqPerson" ON evaluation USING btree ("sqPerson");


--
-- TOC entry 1635 (class 1259 OID 17312)
-- Dependencies: 1256
-- Name: fki_sqchoice; Type: INDEX; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE INDEX fki_sqchoice ON answer USING btree (sqchoice);


--
-- TOC entry 1663 (class 1259 OID 74984)
-- Dependencies: 1269
-- Name: fki_sqcourseevaluation; Type: INDEX; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE INDEX fki_sqcourseevaluation ON module USING btree (sqcourseevaluation);


--
-- TOC entry 1704 (class 1259 OID 74967)
-- Dependencies: 1286
-- Name: fki_sqcriterion; Type: INDEX; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE INDEX fki_sqcriterion ON evaluationanswer USING btree (sqcriterion);


--
-- TOC entry 1705 (class 1259 OID 74978)
-- Dependencies: 1286
-- Name: fki_sqcriterionanswer; Type: INDEX; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE INDEX fki_sqcriterionanswer ON evaluationanswer USING btree (sqcriterionanswer);


--
-- TOC entry 1660 (class 1259 OID 17313)
-- Dependencies: 1268
-- Name: fki_sqforum; Type: INDEX; Schema: public; Owner: amadeus; Tablespace: 
--

CREATE INDEX fki_sqforum ON message USING btree (sqforum);


--
-- TOC entry 1786 (class 2606 OID 99621)
-- Dependencies: 1717 1292 1297
-- Name: _column_sqcolumnmatching_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY _column
    ADD CONSTRAINT _column_sqcolumnmatching_fkey FOREIGN KEY (sqcolumnmatching) REFERENCES columnmatching(sqcolumnmatching);


--
-- TOC entry 1788 (class 2606 OID 99645)
-- Dependencies: 1725 1296 1299
-- Name: alternative_sqmultiplechoice_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY alternative
    ADD CONSTRAINT alternative_sqmultiplechoice_fkey FOREIGN KEY (sqmultiplechoice) REFERENCES multiplechoice(sqmultiplechoice);


--
-- TOC entry 1739 (class 2606 OID 17314)
-- Dependencies: 1668 1271 1255
-- Name: amadeus_user_sqperson_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY amadeususer
    ADD CONSTRAINT amadeus_user_sqperson_fkey FOREIGN KEY (sqperson) REFERENCES person(sqperson);


--
-- TOC entry 1740 (class 2606 OID 17319)
-- Dependencies: 1668 1271 1256
-- Name: answer_sqperson_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY answer
    ADD CONSTRAINT answer_sqperson_fkey FOREIGN KEY (sqperson) REFERENCES person(sqperson);


--
-- TOC entry 1741 (class 2606 OID 17324)
-- Dependencies: 1674 1274 1256
-- Name: answer_sqpoll_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY answer
    ADD CONSTRAINT answer_sqpoll_fkey FOREIGN KEY (sqpoll) REFERENCES poll(sqpoll);


--
-- TOC entry 1784 (class 2606 OID 99600)
-- Dependencies: 1721 1294 1295
-- Name: blank_sqblanksfilling_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY blank
    ADD CONSTRAINT blank_sqblanksfilling_fkey FOREIGN KEY (sqblanksfilling) REFERENCES blanksfilling(sqblanksfilling);


--
-- TOC entry 1783 (class 2606 OID 99588)
-- Dependencies: 1715 1291 1294
-- Name: blanksfilling_sqblanksfilling_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY blanksfilling
    ADD CONSTRAINT blanksfilling_sqblanksfilling_fkey FOREIGN KEY (sqblanksfilling) REFERENCES objectivequestion(sqobjectivequestion);


--
-- TOC entry 1743 (class 2606 OID 17329)
-- Dependencies: 1674 1274 1257
-- Name: choice_sqpoll_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY choice
    ADD CONSTRAINT choice_sqpoll_fkey FOREIGN KEY (sqpoll) REFERENCES poll(sqpoll);


--
-- TOC entry 1781 (class 2606 OID 99567)
-- Dependencies: 1715 1291 1292
-- Name: columnmatching_sqcolumnmatching_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY columnmatching
    ADD CONSTRAINT columnmatching_sqcolumnmatching_fkey FOREIGN KEY (sqcolumnmatching) REFERENCES objectivequestion(sqobjectivequestion);


--
-- TOC entry 1770 (class 2606 OID 74907)
-- Dependencies: 1694 1282 1283
-- Name: commentary_sqcourseevaluation_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY commentary
    ADD CONSTRAINT commentary_sqcourseevaluation_fkey FOREIGN KEY (sqcourseevaluation) REFERENCES courseevaluation(sqcourseevaluation);


--
-- TOC entry 1745 (class 2606 OID 17339)
-- Dependencies: 1638 1258 1259
-- Name: course_keyword_sqcourse_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY course_keyword
    ADD CONSTRAINT course_keyword_sqcourse_fkey FOREIGN KEY (sqcourse) REFERENCES course(sqcourse);


--
-- TOC entry 1746 (class 2606 OID 17344)
-- Dependencies: 1656 1266 1259
-- Name: course_keyword_sqkeyword_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY course_keyword
    ADD CONSTRAINT course_keyword_sqkeyword_fkey FOREIGN KEY (sqkeyword) REFERENCES keyword(sqkeyword);


--
-- TOC entry 1747 (class 2606 OID 17349)
-- Dependencies: 1638 1258 1260
-- Name: course_module_sqcourse_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY course_module
    ADD CONSTRAINT course_module_sqcourse_fkey FOREIGN KEY (sqcourse) REFERENCES course(sqcourse);


--
-- TOC entry 1748 (class 2606 OID 17354)
-- Dependencies: 1664 1269 1260
-- Name: course_module_sqmodule_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY course_module
    ADD CONSTRAINT course_module_sqmodule_fkey FOREIGN KEY (sqmodule) REFERENCES module(sqmodule);


--
-- TOC entry 1744 (class 2606 OID 17334)
-- Dependencies: 1668 1271 1258
-- Name: course_sqperson_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY course
    ADD CONSTRAINT course_sqperson_fkey FOREIGN KEY (sqperson) REFERENCES person(sqperson);


--
-- TOC entry 1749 (class 2606 OID 17359)
-- Dependencies: 1638 1258 1261
-- Name: course_tool_sqcourse_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY course_tool
    ADD CONSTRAINT course_tool_sqcourse_fkey FOREIGN KEY (sqcourse) REFERENCES course(sqcourse);


--
-- TOC entry 1750 (class 2606 OID 17364)
-- Dependencies: 1690 1280 1261
-- Name: course_tool_sqtool_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY course_tool
    ADD CONSTRAINT course_tool_sqtool_fkey FOREIGN KEY (sqtool) REFERENCES tool(sqtool);


--
-- TOC entry 1771 (class 2606 OID 74916)
-- Dependencies: 1694 1282 1284
-- Name: criterion_sqcourseevaluation_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY criterion
    ADD CONSTRAINT criterion_sqcourseevaluation_fkey FOREIGN KEY (sqcourseevaluation) REFERENCES courseevaluation(sqcourseevaluation);


--
-- TOC entry 1776 (class 2606 OID 74943)
-- Dependencies: 1702 1286 1287
-- Name: criterionanswer_sqevaluationanswer_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY criterionanswer
    ADD CONSTRAINT criterionanswer_sqevaluationanswer_fkey FOREIGN KEY (sqevaluationanswer) REFERENCES evaluationanswer(sqevaluationanswer);


--
-- TOC entry 1751 (class 2606 OID 17369)
-- Dependencies: 1650 1264 1262
-- Name: delivery_sqhomework_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY delivery
    ADD CONSTRAINT delivery_sqhomework_fkey FOREIGN KEY (sqhomework) REFERENCES homework(sqhomework);


--
-- TOC entry 1752 (class 2606 OID 17374)
-- Dependencies: 1668 1271 1262
-- Name: delivery_sqperson_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY delivery
    ADD CONSTRAINT delivery_sqperson_fkey FOREIGN KEY (sqperson) REFERENCES person(sqperson);


--
-- TOC entry 1791 (class 2606 OID 99668)
-- Dependencies: 1713 1290 1301
-- Name: discoursive_evaluation_question_sqdiscoursivequestion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY evaluation_discoursive_question
    ADD CONSTRAINT discoursive_evaluation_question_sqdiscoursivequestion_fkey FOREIGN KEY (sqdiscoursivequestion) REFERENCES discoursivequestion(sqdiscoursivequestion);


--
-- TOC entry 1792 (class 2606 OID 124261)
-- Dependencies: 1710 1289 1301
-- Name: discoursive_evaluation_question_sqevaluation_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY evaluation_discoursive_question
    ADD CONSTRAINT discoursive_evaluation_question_sqevaluation_fkey FOREIGN KEY (sqevaluation) REFERENCES evaluation(sqevaluation);


--
-- TOC entry 1779 (class 2606 OID 99526)
-- Dependencies: 1664 1269 1289
-- Name: evaluation_sqmodule_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY evaluation
    ADD CONSTRAINT evaluation_sqmodule_fkey FOREIGN KEY (sqmodule) REFERENCES module(sqmodule);


--
-- TOC entry 1773 (class 2606 OID 74934)
-- Dependencies: 1694 1282 1286
-- Name: evaluationanswer_sqcourseevaluation_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY evaluationanswer
    ADD CONSTRAINT evaluationanswer_sqcourseevaluation_fkey FOREIGN KEY (sqcourseevaluation) REFERENCES courseevaluation(sqcourseevaluation);


--
-- TOC entry 1753 (class 2606 OID 17379)
-- Dependencies: 1664 1269 1263
-- Name: forum_sqmodule_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY forum
    ADD CONSTRAINT forum_sqmodule_fkey FOREIGN KEY (sqmodule) REFERENCES module(sqmodule);


--
-- TOC entry 1756 (class 2606 OID 17384)
-- Dependencies: 1664 1269 1267
-- Name: material_sqmodule_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY material
    ADD CONSTRAINT material_sqmodule_fkey FOREIGN KEY (sqmodule) REFERENCES module(sqmodule);


--
-- TOC entry 1757 (class 2606 OID 17389)
-- Dependencies: 1668 1271 1267
-- Name: material_sqperson_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY material
    ADD CONSTRAINT material_sqperson_fkey FOREIGN KEY (sqperson) REFERENCES person(sqperson);


--
-- TOC entry 1754 (class 2606 OID 17394)
-- Dependencies: 1664 1269 1264
-- Name: materialdelivery_sqmodule_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY homework
    ADD CONSTRAINT materialdelivery_sqmodule_fkey FOREIGN KEY (sqmodule) REFERENCES module(sqmodule);


--
-- TOC entry 1785 (class 2606 OID 99609)
-- Dependencies: 1715 1291 1296
-- Name: multiplechoice_sqmultiplechoice_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY multiplechoice
    ADD CONSTRAINT multiplechoice_sqmultiplechoice_fkey FOREIGN KEY (sqmultiplechoice) REFERENCES objectivequestion(sqobjectivequestion);


--
-- TOC entry 1790 (class 2606 OID 124266)
-- Dependencies: 1710 1289 1300
-- Name: objective_evaluation_question_sqevaluation_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY evaluation_objective_question
    ADD CONSTRAINT objective_evaluation_question_sqevaluation_fkey FOREIGN KEY (sqevaluation) REFERENCES evaluation(sqevaluation);


--
-- TOC entry 1789 (class 2606 OID 99654)
-- Dependencies: 1715 1291 1300
-- Name: objective_evaluation_question_sqobjectivequestion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY evaluation_objective_question
    ADD CONSTRAINT objective_evaluation_question_sqobjectivequestion_fkey FOREIGN KEY (sqobjectivequestion) REFERENCES objectivequestion(sqobjectivequestion);


--
-- TOC entry 1760 (class 2606 OID 17399)
-- Dependencies: 1668 1271 1272
-- Name: person_evaluate_request_sqperson_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY person_evaluate_request
    ADD CONSTRAINT person_evaluate_request_sqperson_fkey FOREIGN KEY (sqperson) REFERENCES person(sqperson);


--
-- TOC entry 1761 (class 2606 OID 17404)
-- Dependencies: 1638 1258 1273
-- Name: person_role_course_sqcourse_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY person_role_course
    ADD CONSTRAINT person_role_course_sqcourse_fkey FOREIGN KEY (sqcourse) REFERENCES course(sqcourse);


--
-- TOC entry 1762 (class 2606 OID 17409)
-- Dependencies: 1668 1271 1273
-- Name: person_role_course_sqperson_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY person_role_course
    ADD CONSTRAINT person_role_course_sqperson_fkey FOREIGN KEY (sqperson) REFERENCES person(sqperson);


--
-- TOC entry 1778 (class 2606 OID 74957)
-- Dependencies: 1694 1282 1288
-- Name: personevaluatecourse_sqcourseevaluation_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY personevaluatecourse
    ADD CONSTRAINT personevaluatecourse_sqcourseevaluation_fkey FOREIGN KEY (sqcourseevaluation) REFERENCES courseevaluation(sqcourseevaluation);


--
-- TOC entry 1777 (class 2606 OID 74952)
-- Dependencies: 1668 1271 1288
-- Name: personevaluatecourse_sqperson_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY personevaluatecourse
    ADD CONSTRAINT personevaluatecourse_sqperson_fkey FOREIGN KEY (sqperson) REFERENCES person(sqperson);


--
-- TOC entry 1763 (class 2606 OID 17414)
-- Dependencies: 1664 1269 1274
-- Name: poll_sqmodule_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_sqmodule_fkey FOREIGN KEY (sqmodule) REFERENCES module(sqmodule);


--
-- TOC entry 1764 (class 2606 OID 17419)
-- Dependencies: 1666 1270 1276
-- Name: profile_permission_sqpermission_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY profile_permission
    ADD CONSTRAINT profile_permission_sqpermission_fkey FOREIGN KEY (sqpermission) REFERENCES permission(sqpermission);


--
-- TOC entry 1772 (class 2606 OID 74925)
-- Dependencies: 1698 1284 1285
-- Name: question_sqcriterion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY question
    ADD CONSTRAINT question_sqcriterion_fkey FOREIGN KEY (sqcriterion) REFERENCES criterion(sqcriterion);


--
-- TOC entry 1765 (class 2606 OID 17424)
-- Dependencies: 1638 1258 1277
-- Name: request_sqcourse_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY request
    ADD CONSTRAINT request_sqcourse_fkey FOREIGN KEY (sqcourse) REFERENCES course(sqcourse);


--
-- TOC entry 1766 (class 2606 OID 17429)
-- Dependencies: 1668 1271 1277
-- Name: request_sqperson_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY request
    ADD CONSTRAINT request_sqperson_fkey FOREIGN KEY (sqperson) REFERENCES person(sqperson);


--
-- TOC entry 1767 (class 2606 OID 17434)
-- Dependencies: 1668 1271 1278
-- Name: resume_sqperson_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY resume
    ADD CONSTRAINT resume_sqperson_fkey FOREIGN KEY (sqperson) REFERENCES person(sqperson);


--
-- TOC entry 1787 (class 2606 OID 99633)
-- Dependencies: 1719 1293 1298
-- Name: sentence_sqtrueorfalse_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY sentence
    ADD CONSTRAINT sentence_sqtrueorfalse_fkey FOREIGN KEY (sqtrueorfalse) REFERENCES trueorfalse(sqtrueorfalse);


--
-- TOC entry 1780 (class 2606 OID 99678)
-- Dependencies: 1668 1271 1289
-- Name: sqPerson; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY evaluation
    ADD CONSTRAINT "sqPerson" FOREIGN KEY ("sqPerson") REFERENCES person(sqperson);


--
-- TOC entry 1742 (class 2606 OID 17439)
-- Dependencies: 1636 1257 1256
-- Name: sqchoice; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY answer
    ADD CONSTRAINT sqchoice FOREIGN KEY (sqchoice) REFERENCES choice(sqchoice) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1759 (class 2606 OID 74979)
-- Dependencies: 1694 1282 1269
-- Name: sqcourseevaluation; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY module
    ADD CONSTRAINT sqcourseevaluation FOREIGN KEY (sqcourseevaluation) REFERENCES courseevaluation(sqcourseevaluation) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1774 (class 2606 OID 74962)
-- Dependencies: 1698 1284 1286
-- Name: sqcriterion; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY evaluationanswer
    ADD CONSTRAINT sqcriterion FOREIGN KEY (sqcriterion) REFERENCES criterion(sqcriterion) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1775 (class 2606 OID 74973)
-- Dependencies: 1706 1287 1286
-- Name: sqcriterionanswer; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY evaluationanswer
    ADD CONSTRAINT sqcriterionanswer FOREIGN KEY (sqcriterionanswer) REFERENCES criterionanswer(sqcriterionanswer) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1758 (class 2606 OID 17444)
-- Dependencies: 1648 1263 1268
-- Name: sqforum; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY message
    ADD CONSTRAINT sqforum FOREIGN KEY (sqforum) REFERENCES forum(sqforum) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1755 (class 2606 OID 17449)
-- Dependencies: 1668 1271 1265
-- Name: sqperson; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY image
    ADD CONSTRAINT sqperson FOREIGN KEY (sqperson) REFERENCES person(sqperson) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 1782 (class 2606 OID 99576)
-- Dependencies: 1715 1291 1293
-- Name: trueorfalse_sqtrueorfalse_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY trueorfalse
    ADD CONSTRAINT trueorfalse_sqtrueorfalse_fkey FOREIGN KEY (sqtrueorfalse) REFERENCES objectivequestion(sqobjectivequestion);


--
-- TOC entry 1768 (class 2606 OID 17454)
-- Dependencies: 1638 1258 1281
-- Name: user_profile_course_sqcourse_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY user_profile_course
    ADD CONSTRAINT user_profile_course_sqcourse_fkey FOREIGN KEY (sqcourse) REFERENCES course(sqcourse);


--
-- TOC entry 1769 (class 2606 OID 17459)
-- Dependencies: 1631 1255 1281
-- Name: user_profile_course_squser_fkey; Type: FK CONSTRAINT; Schema: public; Owner: amadeus
--

ALTER TABLE ONLY user_profile_course
    ADD CONSTRAINT user_profile_course_squser_fkey FOREIGN KEY (squser) REFERENCES amadeususer(squser);
    
-- 
-- Linhas default nas tabelas. Papéis, Perfis e Permissões que devem ser inseridas no momento da instalação do sistema
--
    
INSERT INTO Permission (sqpermission, nmpermission, idpermission) VALUES (1,'matricular-se',4);
INSERT INTO Profile (sqprofile, nmprofile, inintern, idprofile) VALUES (1, PROFESSOR, 0, 0);
INSERT INTO Profile (sqprofile, nmprofile, inintern, idprofile) VALUES (1, STUDENT, 0, 1);
INSERT INTO Profile (sqprofile, nmprofile, inintern, idprofile) VALUES (1, ADMIN, 0, 2);
INSERT INTO Profile_Permission (sqprofile, sqpermission) VALUES (1,1);
INSERT INTO Profile_Permission (sqprofile, sqpermission) VALUES (2,1);
INSERT INTO Profile_Permission (sqprofile, sqpermission) VALUES (3,1);
INSERT INTO Role (sqrole, nmrole, idrole) VALUES (1,'PROFESSOR', 0);
INSERT INTO Role (sqrole, nmrole, idrole) VALUES (1,'STUDENT', 1);
INSERT INTO Role (sqrole, nmrole, idrole) VALUES (1,'MONITOR', 3);


--
-- TOC entry 1796 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2007-01-29 15:46:39 E. South America Standard Time

--
-- PostgreSQL database dump complete
--

