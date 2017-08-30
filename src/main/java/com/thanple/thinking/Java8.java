package com.thanple.thinking;

import lombok.Data;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Thanple on 2017/4/11.
 *
 * Java8的新特性222
 */

public class Java8 {

    private static  class Streams  {
        private enum Status {
            OPEN, CLOSED
        };

        @Data
        @ToString
        private static final class Task {
            private final Status status;
            private final Integer points;
        }
    }


    public static void lambda(){
        Arrays.asList( "a", "b", "d" ).forEach(e -> System.out.print( e +" ") );
        System.out.println();
    }

    public static void optional(){
        Optional< String > fullName = Optional.ofNullable( null );
        System.out.println( "Full Name is set? " + fullName.isPresent() );
        System.out.println( "Full Name: " + fullName.orElseGet( () -> "[none]" ) );
        System.out.println( fullName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );
    }

    public static void stream(){
        final Collection< Streams.Task > tasks = Arrays.asList(
                new Streams.Task( Streams.Status.OPEN, 5 ),
                new Streams.Task( Streams.Status.OPEN, 13 ),
                new Streams.Task( Streams.Status.CLOSED, 8 )
        );

        // Calculate total points of all active tasks using sum()
        final long totalPointsOfOpenTasks = tasks
                .stream()
                .filter( task -> task.getStatus() == Streams.Status.OPEN )
                .mapToInt( Streams.Task::getPoints )
                .sum();

        System.out.println( "Total points: " + totalPointsOfOpenTasks );


        // Calculate total points of all tasks
        final double totalPoints = tasks
                .stream()
                .parallel()
                .map( task -> task.getPoints() ) // or map( Task::getPoints )
                .reduce( 0, Integer::sum );

        System.out.println( "Total points (all tasks): " + totalPoints );

        // Group tasks by their status
        final Map< Streams.Status, List< Streams.Task >> map = tasks
                .stream()
                .collect( Collectors.groupingBy( Streams.Task::getStatus ) );
        System.out.println( map );


        // Calculate the weight of each tasks (as percent of total points)
        final Collection< String > result = tasks
                .stream()                                        // Stream< String >
                .mapToInt( Streams.Task::getPoints )                     // IntStream
                .asLongStream()                                  // LongStream
                .mapToDouble( points -> points / totalPoints )   // DoubleStream
                .boxed()                                         // Stream< Double >
                .mapToLong( weigth -> ( long )( weigth * 100 ) ) // LongStream
                .mapToObj( percentage -> percentage + "%" )      // Stream< String>
                .collect( Collectors.toList() );                 // List< String >

        System.out.println( result );
    }


    public static void main(String[] args) {

        lambda();
        optional();
        stream();

    }


}
