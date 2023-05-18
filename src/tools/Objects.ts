export function requireNonNull<T>(obj: T | undefined): T {
    if (!obj || obj == null) {
        throw new Error("required object is not null")
    }
    return obj
}