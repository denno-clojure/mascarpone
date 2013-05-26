#ifndef _WIN32
  #define EXPORT
#else
  #define EXPORT __declspec(dllexport)
#endif

EXPORT const char* returnsConstantString()
{
  return "This string should be safe to read as const char*";
}

EXPORT int add(int x, int y) {
  return x + y;
}