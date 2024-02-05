export default async (type, name,  version) => {
  const publicPaths = {
    development: "/",
    production: `/static/admin/${type}/${name}/`
  };
  console.log(1111)
  console.log(process.env.NODE_ENV)

  const _import = await import("./_import_" + process.env.NODE_ENV);
  let perfree = await _import.default(type, name, version);
  return perfree.default(publicPaths[process.env.NODE_ENV]);
};
