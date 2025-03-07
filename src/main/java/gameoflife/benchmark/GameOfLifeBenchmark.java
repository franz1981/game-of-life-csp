package gameoflife.benchmark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import gameoflife.ExecutionArgs;
import gameoflife.GameOfLife;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Benchmark)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(3)
public class GameOfLifeBenchmark {

    @Param({"true", "false"})
    private boolean useVirtualThreads;

    @Param({"true", "false"})
    private boolean threadPerCell;

    @Param({"5", "25", "100"}) // 874, 5074, 49324 cells
    private int padding;

    private GameOfLife gameOfLife;

    @Setup
    public void setup() {
        if (!useVirtualThreads && threadPerCell && padding > 50) {
            // This condition causes a OOM, skip it
            return;
        }

        ExecutionArgs args = ExecutionArgs.create(padding, useVirtualThreads, threadPerCell);
        gameOfLife = GameOfLife.create(args);
        gameOfLife.startCells();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public boolean[][] benchmark() {
        return gameOfLife.calculateFrame();
    }
}
