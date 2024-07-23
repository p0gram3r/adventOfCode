package it.p0gram3r.adventofcode.y2021;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Day16Test {

    @ParameterizedTest
    @CsvSource({
            "0, 0000",
            "1, 0001",
            "9, 1001",
            "A, 1010",
            "F, 1111",
            "D2FE28, 110100101111111000101000",
            "38006F45291200, 00111000000000000110111101000101001010010001001000000000",
            "EE00D40C823060, 11101110000000001101010000001100100000100011000001100000",
    })
    void hexToBinary(String input, String expected) {
        assertThat(Day16.hexToBinary(input)).isEqualTo(expected);
    }

    @Test
    void parsePacket_literal() {
        Day16.Packet packet = Day16.parsePacket("110100101111111000101000");

        assertThat(packet.getVersion()).isEqualTo(6);
        assertThat(packet.getTypeId()).isEqualTo(4);
        assertThat(packet).isInstanceOf(Day16.Literal.class);
        assertThat(((Day16.Literal) packet).getValue()).isEqualTo(2021L);
    }

    @Test
    void parsePacket_operator_with_lengthTypeId_0() {
        Day16.Packet packet = Day16.parsePacket("00111000000000000110111101000101001010010001001000000000");

        assertThat(packet.getVersion()).isEqualTo(1);
        assertThat(packet.getTypeId()).isEqualTo(6);
        assertThat(packet).isInstanceOf(Day16.Operator.class);

        Day16.Operator operator = (Day16.Operator) packet;
        assertThat(operator.subPackets.size()).isEqualTo(2);
        assertThat(operator.subPackets.get(0)).isInstanceOf(Day16.Literal.class);
        assertThat(operator.subPackets.get(1)).isInstanceOf(Day16.Literal.class);

        Day16.Literal firstLiteral = (Day16.Literal) operator.subPackets.get(0);
        assertThat(firstLiteral.getValue()).isEqualTo(10L);

        Day16.Literal secondLiteral = (Day16.Literal) operator.subPackets.get(1);
        assertThat(secondLiteral.getValue()).isEqualTo(20L);
    }

    @Test
    void parsePacket_operator_with_lengthTypeId_1() {
        Day16.Packet packet = Day16.parsePacket("11101110000000001101010000001100100000100011000001100000");

        assertThat(packet.getVersion()).isEqualTo(7);
        assertThat(packet.getTypeId()).isEqualTo(3);
        assertThat(packet).isInstanceOf(Day16.Operator.class);

        Day16.Operator operator = (Day16.Operator) packet;
        assertThat(operator.subPackets.size()).isEqualTo(3);
        assertThat(operator.subPackets.get(0)).isInstanceOf(Day16.Literal.class);
        assertThat(operator.subPackets.get(1)).isInstanceOf(Day16.Literal.class);
        assertThat(operator.subPackets.get(2)).isInstanceOf(Day16.Literal.class);

        Day16.Literal firstLiteral = (Day16.Literal) operator.subPackets.get(0);
        assertThat(firstLiteral.getValue()).isEqualTo(1L);

        Day16.Literal secondLiteral = (Day16.Literal) operator.subPackets.get(1);
        assertThat(secondLiteral.getValue()).isEqualTo(2L);

        Day16.Literal thirdLiteral = (Day16.Literal) operator.subPackets.get(2);
        assertThat(thirdLiteral.getValue()).isEqualTo(3L);
    }

    @Test
    void complexTest_1() {
        String input = "8A004A801A8002F478";
        Day16.Packet packet = Day16.parsePacket(Day16.hexToBinary(input));

        assertThat(packet).isInstanceOf(Day16.Operator.class);

        Day16.Operator opLvl1 = (Day16.Operator) packet;
        assertThat(opLvl1.version).isEqualTo(4);
        assertThat(opLvl1.subPackets.size()).isEqualTo(1);
        assertThat(opLvl1.subPackets.get(0)).isInstanceOf(Day16.Operator.class);

        Day16.Operator opLvl2 = (Day16.Operator) opLvl1.subPackets.get(0);
        assertThat(opLvl2.version).isEqualTo(1);
        assertThat(opLvl2.subPackets.size()).isEqualTo(1);
        assertThat(opLvl2.subPackets.get(0)).isInstanceOf(Day16.Operator.class);

        Day16.Operator opLvl3 = (Day16.Operator) opLvl2.subPackets.get(0);
        assertThat(opLvl3.version).isEqualTo(5);
        assertThat(opLvl3.subPackets.size()).isEqualTo(1);
        assertThat(opLvl3.subPackets.get(0)).isInstanceOf(Day16.Literal.class);

        Day16.Literal litLvl4 = (Day16.Literal) opLvl3.subPackets.get(0);
        assertThat(litLvl4.version).isEqualTo(6);

        assertThat(packet.sumVersions()).isEqualTo(16);
    }

    @Test
    void complexTest_2() {
        String input = "620080001611562C8802118E34";
        Day16.Packet packet = Day16.parsePacket(Day16.hexToBinary(input));

        assertThat(packet).isInstanceOf(Day16.Operator.class);

        Day16.Operator opLvl1 = (Day16.Operator) packet;
        assertThat(opLvl1.version).isEqualTo(3);
        assertThat(opLvl1.subPackets.size()).isEqualTo(2);
        assertThat(opLvl1.subPackets.get(0)).isInstanceOf(Day16.Operator.class);
        assertThat(opLvl1.subPackets.get(1)).isInstanceOf(Day16.Operator.class);

        Day16.Operator opLvl2A = (Day16.Operator) opLvl1.subPackets.get(0);
        assertThat(opLvl2A.subPackets.size()).isEqualTo(2);
        assertThat(opLvl2A.subPackets.get(0)).isInstanceOf(Day16.Literal.class);
        assertThat(opLvl2A.subPackets.get(1)).isInstanceOf(Day16.Literal.class);

        Day16.Operator opLvl2B = (Day16.Operator) opLvl1.subPackets.get(1);
        assertThat(opLvl2B.subPackets.size()).isEqualTo(2);
        assertThat(opLvl2B.subPackets.get(0)).isInstanceOf(Day16.Literal.class);
        assertThat(opLvl2B.subPackets.get(1)).isInstanceOf(Day16.Literal.class);

        assertThat(packet.sumVersions()).isEqualTo(12);
    }

    @ParameterizedTest
    @CsvSource({
            "C200B40A82, 3",
            "04005AC33890, 54",
            "880086C3E88112, 7",
            "CE00C43D881120, 9",
            "D8005AC2A8F0, 1",
            "F600BC2D8F, 0",
            "9C005AC2F8F0, 0",
            "9C0141080250320F1802104A08, 1"
    })
    void complexTest_3(String input, long expectedResult) {
        Day16.Packet packet = Day16.parsePacket(Day16.hexToBinary(input));
        assertThat(packet.calculateExpression()).isEqualTo(expectedResult);
    }
}
