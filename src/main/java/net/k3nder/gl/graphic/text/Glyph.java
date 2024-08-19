package net.k3nder.gl.graphic.text;

import net.k3nder.gl.graphic.GraphicalObject;
import net.k3nder.gl.graphic.model.Polygon;
import net.k3nder.gl.graphic.shader.Shader;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Glyph extends GraphicalObject {
      private Shader shader;
      public static final Polygon MODEL =  Polygon.builder()
            .put(-0.5f).put( -0.5f).put( -0.5f).put( 0.0f).put(0.0f).put( -1.0f).put(0.0f).put( 0.0f)
            .put(0.5f ).put(-0.5f ).put(-0.5f).put( 0.0f).put(  0.0f).put( -1.0f).put( 1.0f).put( 0.0f)
            .put(0.5f ).put( 0.5f ).put(-0.5f).put( 0.0f).put( 0.0f).put( -1.0f).put(1.0f).put( 1.0f)
            .put(0.5f ).put( 0.5f ).put(-0.5f).put( 0.0f).put( 0.0f).put( -1.0f).put( 1.0f).put( 1.0f)
            .put(-0.5f).put(  0.5f).put( -0.5f).put(0.0f).put(  0.0f).put( -1.0f).put( 0.0f).put( 1.0f)
            .put(-0.5f).put( -0.5f).put( -0.5f).put( 0.0f).put(  0.0f).put( -1.0f).put( 0.0f).put( 0.0f)
            .put(-0.5f).put( -0.5f).put(  0.5f).put( 0.0f).put(  0.0f).put( 1.0f).put( 0.0f).put( 0.0f)
            .put(0.5f).put(-0.5f).put( 0.5f).put( 0.0f).put(0.0f).put( 1.0f).put( 1.0f).put( 0.0f)
            .put(0.5f).put( 0.5f).put( 0.5f).put( 0.0f).put(0.0f).put( 1.0f).put(  1.0f).put( 1.0f)
            .put(0.5f).put( 0.5f).put( 0.5f).put( 0.0f).put(  0.0f).put( 1.0f).put(  1.0f).put( 1.0f)
            .put(-0.5f).put(  0.5f).put(  0.5f).put ( 0.0f).put(  0.0f).put( 1.0f).put(  0.0f).put( 1.0f)
            .put(-0.5f).put( -0.5f).put(  0.5f).put ( 0.0f).put(  0.0f).put( 1.0f).put(  0.0f).put( 0.0f)
            .put(-0.5f).put(  0.5f).put(  0.5f).put (-1.0f).put(  0.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(-0.5f).put(  0.5f).put( -0.5f).put (-1.0f).put(  0.0f).put(  0.0f).put(  1.0f).put( 1.0f)
            .put(-0.5f).put( -0.5f).put( -0.5f).put (-1.0f).put(  0.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(-0.5f).put( -0.5f).put( -0.5f).put (-1.0f).put(  0.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(-0.5f).put( -0.5f).put(  0.5f).put (-1.0f).put(  0.0f).put(  0.0f).put(  0.0f).put( 0.0f)
            .put(-0.5f).put(  0.5f).put(  0.5f).put (-1.0f).put(  0.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(0.5f ).put( 0.5f ).put( 0.5f).put( 1.0f).put(  0.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(0.5f ).put( 0.5f ).put(-0.5f).put( 1.0f).put(  0.0f).put(  0.0f).put(  1.0f).put( 1.0f)
            .put(0.5f ).put(-0.5f ).put(-0.5f).put( 1.0f).put(  0.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(0.5f ).put(-0.5f ).put(-0.5f).put( 1.0f).put(  0.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(0.5f ).put(-0.5f ).put( 0.5f).put( 1.0f).put(  0.0f).put(  0.0f).put(  0.0f).put( 0.0f)
            .put(0.5f ).put( 0.5f ).put( 0.5f).put( 1.0f).put(  0.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(-0.5f).put( -0.5f).put( -0.5f).put ( 0.0f).put( -1.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(0.5f ).put(-0.5f ).put(-0.5f).put( 0.0f).put( -1.0f).put(  0.0f).put(  1.0f).put( 1.0f)
            .put(0.5f ).put(-0.5f ).put( 0.5f).put( 0.0f).put( -1.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(0.5f ).put(-0.5f ).put( 0.5f).put( 0.0f).put( -1.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(-0.5f).put( -0.5f).put(  0.5f).put ( 0.0f).put( -1.0f).put(  0.0f).put(  0.0f).put( 0.0f)
            .put(-0.5f).put( -0.5f).put( -0.5f).put ( 0.0f).put( -1.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(-0.5f).put(  0.5f).put( -0.5f).put ( 0.0f).put(  1.0f).put(  0.0f).put(  0.0f).put( 1.0f)
            .put(0.5f ).put( 0.5f).put(-0.5f).put( 0.0f).put(  1.0f).put(  0.0f).put(  1.0f).put( 1.0f)
            .put(0.5f).put( 0.5f ).put( 0.5f).put( 0.0f).put(  1.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(0.5f).put( 0.5f ).put( 0.5f).put( 0.0f).put(  1.0f).put(  0.0f).put(  1.0f).put( 0.0f)
            .put(-0.5f).put(  0.5f).put(  0.5f).put ( 0.0f).put(  1.0f).put(  0.0f).put(  0.0f).put( 0.0f)
            .put(-0.5f).put(  0.5f).put( -0.5f).put(  0.0f).put(  1.0f).put(  0.0f).put(  0.0f).put( 1.0f)
           .face(0).face(1).face(2).face(3).face(4).face(5).face(6).face(7).face(8).face(9).face(10).face(11).face(12).face(13).face(14).face(15).face(16).face(17).face(18).face(19).face(20).face(21).face(22).face(23).face(24).face(25).face(26).face(27).face(28).face(29).face(30).face(31).face(32).face(33).face(34).face(35).face(36).face(37).face(38).face(39).face(40).face(41).face(42).face(43).face(44).face(45).face(46).face(47).face(48).face(49).face(50).face(51).face(52).face(53).face(54).face(55).face(56).face(57).face(58).face(59).face(60).face(61).face(62).face(63).face(64).face(65).face(66).face(67).face(68).face(69).face(70).face(71).face(72).face(73).face(74).face(75).face(76).face(77).face(78).face(79).face(80).face(81).face(82).face(83).face(84).face(85).face(86).face(87).face(88).face(89).face(90).face(91).face(92).face(93).face(94).face(95).face(96).face(97).face(98).face(99).face(100).face(101).face(102).face(103).face(104).face(105).face(106).face(107).face(108).face(109).face(110).face(111).face(112).face(113).face(114).face(115).face(116).face(117).face(118).face(119).face(120).face(121).face(122).face(123).face(124).face(125).face(126).face(127).face(128).face(129).face(130).face(131).face(132).face(133).face(134).face(135).face(136).face(137).face(138).face(139).face(140).face(141).face(142).face(143).face(144).face(145).face(146).face(147).face(148).face(149).face(150).face(151).face(152).face(153).face(154).face(155).face(156).face(157).face(158).face(159).face(160).face(161).face(162).face(163).face(164).face(165).face(166).face(167).face(168).face(169).face(170).face(171).face(172).face(173).face(174).face(175).face(176).face(177).face(178).face(179).face(180).face(181).face(182).face(183).face(184).face(185).face(186).face(187).face(188).face(189).face(190).face(191).face(192).face(193).face(194).face(195).face(196).face(197).face(198).face(199).face(200).face(201).face(202).face(203).face(204).face(205).face(206).face(207).face(208).face(209).face(210).face(211).face(212).face(213).face(214).face(215).face(216).face(217).face(218).face(219).face(220).face(221).face(222).face(223).face(224).face(225).face(226).face(227).face(228).face(229).face(230).face(231).face(232).face(233).face(234).face(235).face(236).face(237).face(238).face(239).face(240).face(241).face(242).face(243).face(244).face(245).face(246).face(247).face(248).face(249).face(250).face(251).face(252).face(253).face(254).face(255).face(256).face(257).face(258).face(259).face(260).face(261).face(262).face(263).face(264).face(265).face(266).face(267).face(268).face(269).face(270).face(271).face(272).face(273).face(274).face(275).face(276).face(277).face(278).face(279).face(280).face(281).face(282).face(283).face(284).face(285).face(286).face(287).face(288)

              .attribPointer(0, 3)
              .attribPointer(1, 3)
              .attribPointer(2, 2)

            .build();
    public Glyph(Font loader, char c, Vector2f pos) {
        super();
        texture = loader.getGlyphs().get(c);
        model.translate(new Vector3f(pos.x, pos.y, 0));
        shader = Shader.create(
                """
                #version 330
                
                layout (location = 0) in vec3 aPos;
                layout (location = 1) in vec3 aNormal;
                layout (location = 2) in vec2 aTexCoords;
                
                uniform mat4 model;
                uniform mat4 view;
                uniform mat4 projection;
                
                out vec3 Normal;
                out vec2 TexCoords;
                out vec3 FragPos;
                
                void main() {
                    gl_Position = model * vec4(aPos, 1.0);
                    FragPos = vec3(model * vec4(aPos, 1.0));
                    Normal = aNormal;
                    TexCoords = vec2(aTexCoords.x, aTexCoords.y);
                }
                """,
                """
                #version 330
                
                out vec4 FragColor;
                
                in vec2 TexCoords;
                
                uniform sampler2D tex;
                
                void main() {
                    FragColor = texture(tex, TexCoords);
                }
                """);
    }
    @Override
    public void render(Shader shader) {
        this.shader.use();
        super.render(this.shader);
    }
    @Override
    public void load() {
        polygon = MODEL;
    }
}
