export type DeepRequired<T> =
  T extends Function
    ? T
    : T extends Date
      ? Date
      : T extends Array<infer U>
        ? Array<DeepRequired<U>>
        : T extends ReadonlyArray<infer U>
          ? ReadonlyArray<DeepRequired<U>>
          : T extends Map<infer K, infer V>
            ? Map<DeepRequired<K>, DeepRequired<V>>
            : T extends Set<infer U>
              ? Set<DeepRequired<U>>
              : T extends object
                ? { [K in keyof T]-?: DeepRequired<T[K]> }
                : NonNullable<T>;