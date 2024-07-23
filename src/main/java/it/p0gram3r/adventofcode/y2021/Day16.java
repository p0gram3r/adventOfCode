package it.p0gram3r.adventofcode.y2021;

import static java.lang.Integer.toBinaryString;
import static java.lang.Integer.toHexString;
import static org.apache.commons.lang3.StringUtils.leftPad;

import it.p0gram3r.adventofcode.Puzzle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

public class Day16 implements Puzzle {

    @Override
    public String solutionA(List<String> input) {
        String hex = input.get(0);
        Packet packet = parsePacket(hexToBinary(hex));

        return Long.toString(packet.sumVersions());
    }

    @Override
    public String solutionB(List<String> input) {
        String hex = input.get(0);
        Packet packet = parsePacket(hexToBinary(hex));

        return Long.toString(packet.calculateExpression());
    }

    static Map<String, String> hexToBinaryMapping = new HashMap<>(16);

    static {
        IntStream.range(0, 16).forEach(i -> {
            String hex = toHexString(i).toUpperCase();
            String binary = leftPad(toBinaryString(i), 4, "0");
            hexToBinaryMapping.put(hex, binary);
        });
    }

    static String hexToBinary(String hex) {
        return hex.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .map(String::toUpperCase)
                .map(s -> hexToBinaryMapping.get(s))
                .collect(Collectors.joining());
    }

    static Packet parsePacket(String binaryString) {
        int version = Integer.parseInt(binaryString.substring(0, 3), 2);
        int typeId = Integer.parseInt(binaryString.substring(3, 6), 2);

        return (typeId == 4)
                ? parseLiteral(version, typeId, binaryString.substring(6))
                : parseOperator(version, typeId, binaryString.substring(6));
    }

    private static Literal parseLiteral(int version, int typeId, String encodedLiteral) {
        int bitSize = 6;

        StringBuilder temp = new StringBuilder();
        for (int index = 0; ; index += 5) {
            temp.append(encodedLiteral, index + 1, index + 5);
            bitSize += 5;
            if (encodedLiteral.charAt(index) == '0') {
                break;
            }
        }
        long value = Long.parseLong(temp.toString(), 2);

        return new Literal(version, typeId, bitSize, value);
    }

    private static Operator parseOperator(int version, int typeId, String subPackets) {
        var builder = Operator.builder()
                .version(version)
                .typeId(typeId);

        char lengthTypeId = subPackets.charAt(0);
        if (lengthTypeId == '0') {
            // the next 15 bits are a number that represents the total length in bits of the sub-packets contained by this packet.
            int lengthOfSubPackets = Integer.parseInt(subPackets.substring(1, 16), 2);

            for (int tempBitSize = 0; tempBitSize < lengthOfSubPackets;) {
                Packet packet = parsePacket(subPackets.substring(16 + tempBitSize));
                builder.subPacket(packet);
                tempBitSize += packet.bitSize;
            }
            builder.bitSize(6 + 1 + 15 + lengthOfSubPackets);
        }

        //
        else if (lengthTypeId == '1') {
            // the next 11 bits are a number that represents the number of sub-packets immediately contained by this packet.
            int numberOfSubPackets = Integer.parseInt(subPackets.substring(1, 12), 2);

            int tempBitSize = 0;
            for (int subPacketsParsed = 0; subPacketsParsed < numberOfSubPackets; subPacketsParsed++) {
                Packet packet = parsePacket(subPackets.substring(12 + tempBitSize));
                builder.subPacket(packet);
                tempBitSize += packet.bitSize;
            }
            builder.bitSize(6 + 1 + 11 + tempBitSize);
        }

        //
        else {
            throw new IllegalArgumentException("lengthTypeId is not a binary value: " + subPackets);
        }

        return builder.build();
    }

    @SuperBuilder
    @RequiredArgsConstructor
    @Getter
    @FieldDefaults(makeFinal = true)
    static abstract class Packet {
        int version;
        int typeId;
        int bitSize;

        public abstract long sumVersions();

        public abstract long calculateExpression();
    }

    @Getter
    @FieldDefaults(makeFinal = true)
    static class Literal extends Packet {
        long value;

        public Literal(int version, int typeId, int bitSize, long value) {
            super(version, typeId, bitSize);
            this.value = value;
        }

        public String toString() {
            return "(L: " + value + ")";
        }

        @Override
        public long sumVersions() {
            return version;
        }

        @Override
        public long calculateExpression() {
            return value;
        }
    }

    @SuperBuilder
    @Getter
    @FieldDefaults(makeFinal = true)
    static class Operator extends Packet {
        @Singular
        List<Packet> subPackets;

        public String toString() {
            StringBuilder sb = new StringBuilder("(O: ");
            subPackets.forEach(p -> sb.append(p.toString()).append(" "));
            sb.append(")");
            return sb.toString();
        }

        @Override
        public long sumVersions() {
            long versionsOfSubPackets = subPackets.stream().map(Packet::sumVersions).mapToLong(i -> i).sum();
            return version + versionsOfSubPackets;
        }

        @Override
        public long calculateExpression() {
            return switch (typeId) {
                case 0 -> subPackets.stream().map(Packet::calculateExpression).mapToLong(i->i).sum();
                case 1 -> subPackets.stream().map(Packet::calculateExpression).mapToLong(i->i).reduce(1L, (a, b) -> a * b);
                case 2 -> subPackets.stream().map(Packet::calculateExpression).mapToLong(i->i).min().orElseThrow();
                case 3 -> subPackets.stream().map(Packet::calculateExpression).mapToLong(i->i).max().orElseThrow();
                case 5 -> subPackets.get(0).calculateExpression() > subPackets.get(1).calculateExpression() ? 1 : 0;
                case 6 -> subPackets.get(0).calculateExpression() < subPackets.get(1).calculateExpression() ? 1 : 0;
                case 7 -> subPackets.get(0).calculateExpression() == subPackets.get(1).calculateExpression() ? 1 : 0;
                default -> throw new IllegalStateException("Unexpected value: " + typeId);
            };
        }
    }

}

