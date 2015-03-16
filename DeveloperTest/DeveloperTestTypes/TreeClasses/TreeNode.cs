using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections.ObjectModel;

namespace DeveloperTest.TreeClasses
{
    public class TreeNode
    {
        public TreeNode Parent { get; private set; }
        private List<TreeNode> _internalChildren;
        private ReadOnlyCollection<TreeNode> _children;
        public ReadOnlyCollection<TreeNode> Children
        {
            get
            {
                if (_children == null)
                {
                    _children = new ReadOnlyCollection<TreeNode>(_internalChildren);
                }
                return _children;
            }
        }
        public int Id { get; private set; }

        public TreeNode(int id)
        {
            Id = id;
            _internalChildren = new List<TreeNode>();
        }

        public void AddChild(TreeNode id)
        {
            id.Parent = this;
            _internalChildren.Add(id);
        }

        public void Traverse(Action<TreeNode> visit)
        {
            visit(this);
            foreach (var child in Children)
            {
                child.Traverse(visit);
            }
        }
    }
}
