def find(str, ch, start=0, end=-1): 
  index = start 
  while index < len(str):
      if index == str.index(str[end]):
          break 
      if str[index] == ch: 
          return index 
      index = index + 1 
  return -1 