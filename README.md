# 2022-08-21

- [X] What is `ForkJoinPool`?

```java
ForkJoinPool.commonPool().invokeAll(Arrays.asList(applePicker1, applePicker2, applePicker3));
```

- [ ] What is `deadlock`?
- [X] Differences between `RecursiveTask` and `RecursiveAction`?

    - RecursiveTask<T>, `compute():T`
    - RecursiveAction, `compute():void`

- [X] Differences between `ForkJoinPool.invlove()` and `execute()`?
    - `invoke`, completes the given task
    - `execute`, arranges asynchronous, don't wait for them to complete 

- [ ] `completeExceptionally()`, nothing Failed?


- [ ] Differences between `invokeAll(Callables):List<Future<T>>` and `invoke(task):T` ?

