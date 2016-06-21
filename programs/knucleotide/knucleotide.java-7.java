/* The Computer Language Benchmarks Game
 http://benchmarksgame.alioth.debian.org/

 contributed by Clinton Begin
 based on submission by James McIlree, Matthieu Bentot, The Anh Tran and Andy Fingerhut
 */

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class knucleotide {

    public static void main(final String[] args) throws Exception {
   // read full fullSequence
   final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
   scanPastHeader(in, ">THREE");
   final byte[] fullSequence = readRestIntoByteArray(in);

   // create tasks
   final List<Callable<Map<Integer, Nucleotide>>> nucleotideMaps = buildCountTasks(fullSequence);

   // execute tasks
   final ExecutorService executor = new ForkJoinPool();
   final List<Future<Map<Integer, Nucleotide>>> futures = executor.invokeAll(nucleotideMaps);
   executor.shutdown();

   // print frequencies
   final StringBuilder out = new StringBuilder();
   printFrequencies(out, fullSequence, futures.get(0).get());
   printFrequencies(out, fullSequence, sumMaps(futures.get(1).get(), futures.get(2).get()));

   // print counts
   final String[] nucleotideFrequenciesToPrint = {"ggt", "ggta", "ggtatt", "ggtattttaatt", "ggtattttaatttatagt"};
   for (final String nucleotideSequence : nucleotideFrequenciesToPrint) {
       printCount(out, futures, nucleotideSequence);
   }

   System.out.print(out.toString());
    }

    private static List<Callable<Map<Integer, Nucleotide>>> buildCountTasks(final byte[] fullSequence) {
   final int[] nucleotideLengths = {1, 2, 3, 4, 6, 12, 18};
   final List<Callable<Map<Integer, Nucleotide>>> nucleotideMaps = new LinkedList<>();
   for (final int nucleotideLength : nucleotideLengths) {
       for (int i = 0; i < nucleotideLength; i++) {
      final int offset = i;
      nucleotideMaps.add(new Callable<Map<Integer, Nucleotide>>() {
          @Override
          public Map<Integer, Nucleotide> call() throws Exception {
         return countSequences(fullSequence, nucleotideLength, offset);
          }
      });
       }
   }
   return nucleotideMaps;
    }

    private static byte[] readRestIntoByteArray(final BufferedReader in) throws IOException {
   final ByteArrayOutputStream baos = new ByteArrayOutputStream();
   final byte bytes[] = new byte[256];
   String line;
   while ((line = in.readLine()) != null) {
       for (int i = 0; i < line.length(); i++)
      bytes[i] = (byte) line.charAt(i);
       baos.write(bytes, 0, line.length());
   }
   return baos.toByteArray();
    }

    private static void scanPastHeader(final BufferedReader in, final String header) throws IOException {
   String line;
   while ((line = in.readLine()) != null) {
       if (line.startsWith(header)) break;
   }
    }

    private static Map<Integer, Nucleotide> countSequences(final byte[] fullSequence, final int nucleotideLength, final int offset) {
   final Map<Integer, Nucleotide> nucleotideMap = new HashMap<>();
   for (int i = offset; i < fullSequence.length - nucleotideLength; i += nucleotideLength) {
       final int hash = calculateHash(fullSequence, i, nucleotideLength);
       final Nucleotide nucleotide = nucleotideMap.get(hash);
       if (nucleotide == null) {
      nucleotideMap.put(hash, new Nucleotide(fullSequence, i, nucleotideLength));
       } else {
      nucleotide.count++;
       }
   }
   return nucleotideMap;
    }

    private static Map<Integer, Nucleotide> sumMaps(final Map<Integer, Nucleotide>... nucleotideMaps) {
   final Map<Integer, Nucleotide> totalNucleotideMap = new HashMap<>();
   for (Map<Integer, Nucleotide> nucleotideMap : nucleotideMaps) {
       for (Map.Entry<Integer, Nucleotide> entry : nucleotideMap.entrySet()) {
      final Nucleotide sum = totalNucleotideMap.get(entry.getKey());
      if (sum != null)
          sum.count += entry.getValue().count;
      else
          totalNucleotideMap.put(entry.getKey(), entry.getValue());
       }
   }
   return totalNucleotideMap;
    }

    private static int calculateHash(final byte[] bytes, final int offset, final int length) {
   int hash = 17;
   for (int i = 0; i < length; i++) {
       hash = hash * 23 + bytes[offset + i];
   }
   return hash;
    }

    private static void printCount(final StringBuilder out, final List<Future<Map<Integer, Nucleotide>>> nucleotideMapFutures, final String nucleotideSequence) throws Exception {
   final int hash = calculateHash(nucleotideSequence.getBytes(), 0, nucleotideSequence.length());
   int count = 0;
   for (Future<Map<Integer, Nucleotide>> future : nucleotideMapFutures) {
       final Nucleotide nucleotide = future.get().get(hash);
       if (nucleotide != null) count += nucleotide.count;
   }
   out.append(count).append("\t").append(nucleotideSequence.toUpperCase()).append("\n");
    }

    private static void printFrequencies(final StringBuilder out, final byte[] fullSequence, Map<Integer, Nucleotide> nucleotideMap) {
   final TreeSet<Nucleotide> nucleotides = new TreeSet<>(nucleotideMap.values());
   for (final Nucleotide value : nucleotides) {
       final String sequenceString = value.toString();
       final double factor = 100d / (double) (fullSequence.length - value.length + 1);
       final double frequency = ((double) value.count * factor);
       final String formatted = String.format("%s %.3f", sequenceString, frequency);
       out.append(formatted);
       out.append("\n");
   }
   out.append("\n");
    }

    private static class Nucleotide implements Comparable<Nucleotide> {
   private final byte[] fullSequence;
   private final int offset;
   private final int length;
   private int count = 1;

   private Nucleotide(final byte[] fullSequence, final int offset, final int length) {
       this.fullSequence = fullSequence;
       this.offset = offset;
       this.length = length;
   }

   public final int compareTo(final Nucleotide other) {
       if (other.count != count) {
      // For primary sort by count
      return other.count - count;
       } else {
      // For secondary sort by bytes
      return compareBytes(this, other);
       }
   }

   private int compareBytes(final Nucleotide a, final Nucleotide b) {
       final int suma = sumBytes(a);
       final int sumb = sumBytes(b);
       return suma < sumb ? -1 : suma == sumb ? 0 : 1;
   }

   private int sumBytes(final Nucleotide nucleotide) {
       int sum = 0;
       for (int i = 0; i < nucleotide.length; i++) {
      sum += nucleotide.fullSequence[nucleotide.offset + i] * (nucleotide.length-i);
       }
       return sum;
   }

   @Override
   public String toString() {
       return new String(fullSequence, offset, length).toUpperCase();
   }
    }

}
