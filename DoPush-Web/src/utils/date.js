export function transTimestampToDate(timestamp) {
  const date = new Date(timestamp * 1000)
  return date.toLocaleDateString().replace(/\//g, '-') + ' ' + date.toTimeString().slice(0, 5)
}
